package lyzzcw.stupid.spring.statemachine.service.impl;

import lombok.extern.slf4j.Slf4j;
import lyzzcw.stupid.spring.statemachine.constants.Constant;
import lyzzcw.stupid.spring.statemachine.domain.Order;
import lyzzcw.stupid.spring.statemachine.enums.OrderState;
import lyzzcw.stupid.spring.statemachine.enums.OrderStateChangeEvent;
import lyzzcw.stupid.spring.statemachine.mapper.OrderMapper;
import lyzzcw.stupid.spring.statemachine.service.OrderService;
import lyzzcw.stupid.spring.statemachine.support.StateEventResult;
import lyzzcw.stupid.spring.statemachine.support.StateMachineManager;
import lyzzcw.work.component.domain.common.constant.HttpStatus;
import lyzzcw.work.component.domain.common.exception.BaseException;
import lyzzcw.work.component.redis.lock.DistributedLock;
import lyzzcw.work.component.redis.lock.factory.DistributedLockFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Luiz
 * @version 1.0
 * Date: 2024/12/6 13:33
 * Description: No Description
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Resource
    private StateMachineManager stateMachineManager;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private DistributedLockFactory distributedLockFactory;

    /**
     * 确认下单
     */
    @Override
    public Order confirm(Order order) {
        order.setStatus(OrderState.WAIT_PAYMENT.getKey());
        order.setCreateTime(LocalDateTime.now());
        orderMapper.insert(order);
        return order;
    }

    /**
     * 支付订单
     */
    @Override
    public void pay(Long id) {
        Order order = orderMapper.selectById(id);
        log.info("线程名称：{},尝试支付，订单号：{}", Thread.currentThread().getName(), id);
        StateEventResult<OrderState,OrderStateChangeEvent> result =
                sendEvent(OrderStateChangeEvent.PAYED, order,Constant.payTransition);
        if (!result.isResult()) {
            log.info("线程名称：{}，支付失败，状态异常：{}，订单信息：{}",Thread.currentThread().getName(),result,order);
            throw new BaseException(result.getMessage());
        }
    }

    /**
     * 取消支付
     */
    @Override
    public void cancel(Long id) {
        Order order = orderMapper.selectById(id);
        log.info("线程名称：{},取消支付，订单号：{}", Thread.currentThread().getName(), id);
        StateEventResult<OrderState,OrderStateChangeEvent> result =
                sendEvent(OrderStateChangeEvent.CANCEL_PAYED, order,Constant.cancelTransition);
        if (!result.isResult()) {
            log.info("线程名称：{},取消支付失败, 状态异常：{}，订单信息：{}", Thread.currentThread().getName(),result, order);
            throw new BaseException(result.getMessage());
        }
    }

    /**
     * 对订单进行发货
     */
    @Override
    public void deliver(Long id) {
        Order order = orderMapper.selectById(id);
        log.info("线程名称：{},尝试发货，订单号：{}", Thread.currentThread().getName(), id);
        StateEventResult<OrderState,OrderStateChangeEvent> result =
                sendEvent(OrderStateChangeEvent.DELIVERY, order,Constant.deliverTransition);
        if (!result.isResult()) {
            log.error("线程名称：{},发货失败, 状态异常：{}，订单信息：{}", Thread.currentThread().getName(),result, order);
            throw new BaseException(result.getMessage());
        }
    }

    /**
     * 确认收货
     */
    @Override
    public void receive(Long id) {
        Order order = orderMapper.selectById(id);
        log.info("线程名称：{},确认收货，订单号：{}", Thread.currentThread().getName(), id);
        StateEventResult<OrderState,OrderStateChangeEvent> result =
                sendEvent(OrderStateChangeEvent.RECEIVED, order,Constant.receiveTransition);
        if (!result.isResult()) {
            log.error("线程名称：{},收货失败, 状态异常：{}，订单信息：{}", Thread.currentThread().getName(),result, order);
            throw new BaseException(result.getMessage());
        }
    }


    @Override
    public Order getById(Long id) {
        return orderMapper.selectById(id);
    }

    /**
     * 发送订单状态转换事件
     * synchronized暂时修饰保证这个方法是线程安全的
     * 正常生产代码可用redis锁住订单id即可
     */
    private StateEventResult<OrderState,OrderStateChangeEvent> sendEvent(OrderStateChangeEvent changeEvent, Order order, String key) {
        String redisLockKey = Constant.orderRedisLockPrefix.concat(String.valueOf(order.getId()));
        DistributedLock lock = distributedLockFactory.getDistributedLock(redisLockKey);
        try {
            if (!lock.tryLock(2, 5, TimeUnit.SECONDS)){
                throw new BaseException(HttpStatus.TOO_MANY_REQUESTS);
            }
            //redis持久化状态机尝试恢复状态机状态
            stateMachineManager.initialize(Constant.orderRedisPrefix.concat(String.valueOf(order.getId())));
            StateMachine<OrderState,OrderStateChangeEvent> orderStateMachine = stateMachineManager.getStateMachine();
            Message<OrderStateChangeEvent> message = MessageBuilder.withPayload(changeEvent).setHeader(Constant.orderHeader, order).build();
            boolean result = orderStateMachine.sendEvent(message);
            StateEventResult<OrderState,OrderStateChangeEvent> stateEventResult = new StateEventResult<>();
            stateEventResult.setEvent(changeEvent);
            stateEventResult.setState(stateMachineManager.getStateMachine().getState().getId());
            if(!result){
                //状态机事件发送异常
                log.error("send state event failed : {}", order.getId());
                stateEventResult.setResult(false);
                stateEventResult.setMessage("订单消息事件订阅失败, 当前订单流转状态:" + stateEventResult.getState().getDesc());
                return stateEventResult;
            }
            //获取到监听的结果信息
            Integer o = (Integer) orderStateMachine.getExtendedState().getVariables().get(key + order.getId());
            //操作完成之后,删除本次对应的key信息
            orderStateMachine.getExtendedState().getVariables().remove(key+order.getId());
            //如果事务执行成功，则持久化状态机
            if(Objects.equals(Constant.ORDER_STATE_RESULT_SUCCESS, o)){
                //使用redis持久化状态机状态机状态,分布式环境下使用
                stateMachineManager.persist(Constant.orderRedisPrefix.concat(String.valueOf(order.getId())));
                stateEventResult.setResult(true);
                stateEventResult.setMessage("success");
                return stateEventResult;
            }else {
                //订单执行业务异常
                log.error("order state machine handler failed : {}", order.getId());
                stateEventResult.setResult(false);
                stateEventResult.setMessage("订单业务执行失败, 当前订单流转状态" + stateEventResult.getState().getDesc());
                return stateEventResult;
            }
        } catch (Exception e) {
            log.error("订单操作失败:{}", e.getMessage(),e);
        }finally {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
        return null;
    }
}
