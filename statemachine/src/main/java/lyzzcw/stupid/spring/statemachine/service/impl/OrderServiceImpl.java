package lyzzcw.stupid.spring.statemachine.service.impl;

import lombok.extern.slf4j.Slf4j;
import lyzzcw.stupid.spring.statemachine.constants.CommonConstants;
import lyzzcw.stupid.spring.statemachine.domain.Order;
import lyzzcw.stupid.spring.statemachine.enums.OrderStatus;
import lyzzcw.stupid.spring.statemachine.enums.OrderStatusChangeEvent;
import lyzzcw.stupid.spring.statemachine.mapper.OrderMapper;
import lyzzcw.stupid.spring.statemachine.service.OrderService;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
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
    private StateMachine<OrderStatus, OrderStatusChangeEvent> orderStateMachine;
    @Resource
    private StateMachinePersister<OrderStatus, OrderStatusChangeEvent, String> stateMachineMemPersister;
    @Resource
    private StateMachinePersister<OrderStatus, OrderStatusChangeEvent, String> stateMachineRedisPersister;
    @Resource
    private OrderMapper orderMapper;

    /**
     * 确认下单
     */
    @Override
    public Order confirm(Order order) {
        order.setStatus(OrderStatus.WAIT_PAYMENT.getKey());
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
        if (!sendEvent(OrderStatusChangeEvent.PAYED, order,CommonConstants.payTransition)) {
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
        if (!sendEvent(OrderStatusChangeEvent.CANCEL_PAYED, order,CommonConstants.cancelTransition)) {
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
        if (!sendEvent(OrderStatusChangeEvent.DELIVERY, order,CommonConstants.deliverTransition)) {
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
        if (!sendEvent(OrderStatusChangeEvent.RECEIVED, order,CommonConstants.receiveTransition)) {
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
     * synchronized修饰保证这个方法是线程安全的
     */
    private synchronized boolean sendEvent(OrderStatusChangeEvent changeEvent, Order order,String key) {
        boolean result = false;
        try {
            //启动状态机
            orderStateMachine.startReactively();

            //内存持久化状态机尝试恢复状态机状态,单机环境下使用
            //stateMachineMemPersister.restore(orderStateMachine, String.valueOf(order.getId()));

            //redis持久化状态机尝试恢复状态机状态,分布式环境下使用
            stateMachineRedisPersister.restore(orderStateMachine,
                    CommonConstants.orderRedisPrefix.concat(String.valueOf(order.getId())));

            Message<OrderStatusChangeEvent> message = MessageBuilder.withPayload(changeEvent).setHeader(CommonConstants.orderHeader, order).build();
            result = orderStateMachine.sendEvent(message);
            if(!result){
                return false;
            }
            //获取到监听的结果信息
            Integer o = (Integer) orderStateMachine.getExtendedState().getVariables().get(key + order.getId());
            //操作完成之后,删除本次对应的key信息
            orderStateMachine.getExtendedState().getVariables().remove(key+order.getId());
            //如果事务执行成功，则持久化状态机
            if(Objects.equals(1, o)){
                //使用内存持久化状态机状态,单机环境下使用
                //stateMachineMemPersister.persist(orderStateMachine, String.valueOf(order.getId()));
                //使用redis持久化状态机状态机状态,分布式环境下使用
                stateMachineRedisPersister.persist(orderStateMachine,
                        CommonConstants.orderRedisPrefix.concat(String.valueOf(order.getId())));
            }else {
                //订单执行业务异常
                return false;
            }
        } catch (Exception e) {
            log.error("订单操作失败:{}", e.getMessage(),e);
        } finally {
            orderStateMachine.stopReactively();
        }
        return result;
    }
}
