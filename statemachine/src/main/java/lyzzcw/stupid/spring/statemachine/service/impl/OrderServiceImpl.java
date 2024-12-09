package lyzzcw.stupid.spring.statemachine.service.impl;

import lombok.extern.slf4j.Slf4j;
import lyzzcw.stupid.spring.statemachine.constants.Constant;
import lyzzcw.stupid.spring.statemachine.domain.Order;
import lyzzcw.stupid.spring.statemachine.enums.OrderState;
import lyzzcw.stupid.spring.statemachine.enums.OrderStateChangeEvent;
import lyzzcw.stupid.spring.statemachine.mapper.OrderMapper;
import lyzzcw.stupid.spring.statemachine.service.OrderService;
import lyzzcw.stupid.spring.statemachine.support.StateMachineManager;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Objects;

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
        if (!sendEvent(OrderStateChangeEvent.PAYED, order,Constant.payTransition)) {
            log.error("线程名称：{},支付失败, 状态异常，订单信息：{}", Thread.currentThread().getName(), order);
            throw new RuntimeException("支付失败, 订单状态异常");
        }
    }

    /**
     * 取消支付
     */
    @Override
    public void cancel(Long id) {
        Order order = orderMapper.selectById(id);
        log.info("线程名称：{},取消支付，订单号：{}", Thread.currentThread().getName(), id);
        if (!sendEvent(OrderStateChangeEvent.CANCEL_PAYED, order,Constant.cancelTransition)) {
            log.error("线程名称：{},取消支付失败, 状态异常，订单信息：{}", Thread.currentThread().getName(), order);
            throw new RuntimeException("支付失败, 订单状态异常");
        }
    }

    /**
     * 对订单进行发货
     */
    @Override
    public void deliver(Long id) {
        Order order = orderMapper.selectById(id);
        log.info("线程名称：{},尝试发货，订单号：{}", Thread.currentThread().getName(), id);
        if (!sendEvent(OrderStateChangeEvent.DELIVERY, order,Constant.deliverTransition)) {
            log.error("线程名称：{},发货失败, 状态异常，订单信息：{}", Thread.currentThread().getName(), order);
            throw new RuntimeException("发货失败, 订单状态异常");
        }
    }

    /**
     * 确认收货
     */
    @Override
    public void receive(Long id) {
        Order order = orderMapper.selectById(id);
        log.info("线程名称：{},确认收货，订单号：{}", Thread.currentThread().getName(), id);
        if (!sendEvent(OrderStateChangeEvent.RECEIVED, order,Constant.receiveTransition)) {
            log.error("线程名称：{},收货失败, 状态异常，订单信息：{}", Thread.currentThread().getName(), order);
            throw new RuntimeException("发货失败, 订单状态异常");
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
    private synchronized boolean sendEvent(OrderStateChangeEvent changeEvent, Order order, String key) {
        boolean result = false;
        try {
            //redis持久化状态机尝试恢复状态机状态
            stateMachineManager.initialize(Constant.orderRedisPrefix.concat(String.valueOf(order.getId())));
            StateMachine<OrderState,OrderStateChangeEvent> orderStateMachine = stateMachineManager.getStateMachine();
            Message<OrderStateChangeEvent> message = MessageBuilder.withPayload(changeEvent).setHeader(Constant.orderHeader, order).build();
            result = orderStateMachine.sendEvent(message);
            if(!result){
                return false;
            }
            //获取到监听的结果信息
            Integer o = (Integer) orderStateMachine.getExtendedState().getVariables().get(key + order.getId());
            //操作完成之后,删除本次对应的key信息
            orderStateMachine.getExtendedState().getVariables().remove(key+order.getId());
            //如果事务执行成功，则持久化状态机
            if(Objects.equals(Constant.ORDER_STATE_RESULT_SUCCESS, o)){
                //使用redis持久化状态机状态机状态,分布式环境下使用
                stateMachineManager.persist(Constant.orderRedisPrefix.concat(String.valueOf(order.getId())));
            }else {
                //订单执行业务异常
                log.error("order state machine event failed : {}", order.getId());
                return false;
            }
        } catch (Exception e) {
            log.error("订单操作失败:{}", e.getMessage(),e);
        }
        return result;
    }
}
