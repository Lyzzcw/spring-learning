package lyzzcw.stupid.spring.statemachine.listener;

import lombok.extern.slf4j.Slf4j;
import lyzzcw.stupid.spring.statemachine.constants.CommonConstants;
import lyzzcw.stupid.spring.statemachine.domain.Order;
import lyzzcw.stupid.spring.statemachine.enums.OrderStatus;
import lyzzcw.stupid.spring.statemachine.enums.OrderStatusChangeEvent;
import lyzzcw.stupid.spring.statemachine.mapper.OrderMapper;
import lyzzcw.work.component.common.utils.AssertUtils;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author Luiz
 * @version 1.0
 * Date: 2024/12/6 13:46
 * Description: 订单事件监听器(注释调的代码替换为aop拦截实现LogResultAspect)
 */
@Component("orderStateListener")
@WithStateMachine(name = "orderStateMachine")
@Slf4j
public class OrderStateListener {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private StateMachine<OrderStatus, OrderStatusChangeEvent> orderStateMachine;

    /**
     * 取消支付事件监听
     */
    @OnTransition(source = "WAIT_PAYMENT", target = "CLOSED")
    @Transactional(rollbackFor = Exception.class)
    public void cancelTransition(Message<OrderStatusChangeEvent> message) {
        Order order = (Order) message.getHeaders().get(CommonConstants.orderHeader);
        AssertUtils.notNull(order, "取消支付，状态机订单信息异常");
        log.info("取消支付，状态机反馈信息：{}", message.getHeaders());
        try {
            order.setStatus(OrderStatus.CLOSED.getKey());
            order.setUpdateTime(LocalDateTime.now());
            order.setDeleteFlag(1);
            orderMapper.updateById(order);
            //成功 则为1
            orderStateMachine.getExtendedState().getVariables().put(CommonConstants.cancelTransition+order.getId(),1);
        } catch (Exception e) {
            //如果出现异常，则进行回滚
            log.error("cancelTransition 出现异常：{}",e.getMessage(),e);
            //将异常信息变量信息中，失败则为0
            orderStateMachine.getExtendedState().getVariables().put(CommonConstants.cancelTransition+order.getId(), 0);
            throw e;
        }
    }

    /**
     * 支付事件监听
     */
    @OnTransition(source = "WAIT_PAYMENT", target = "WAIT_DELIVER")
    @Transactional(rollbackFor = Exception.class)
    public void payTransition(Message<OrderStatusChangeEvent> message) {
        Order order = (Order) message.getHeaders().get(CommonConstants.orderHeader);
        AssertUtils.notNull(order, "支付，状态机订单信息异常");
        log.info("支付，状态机反馈信息：{}", message.getHeaders());
        try {
            order.setStatus(OrderStatus.WAIT_DELIVER.getKey());
            order.setUpdateTime(LocalDateTime.now());
            orderMapper.updateById(order);
            //成功 则为1
            orderStateMachine.getExtendedState().getVariables().put(CommonConstants.payTransition+order.getId(),1);
        } catch (Exception e) {
            //如果出现异常，则进行回滚
            log.error("payTransition 出现异常：{}",e.getMessage(),e);
            //将异常信息变量信息中，失败则为0
            orderStateMachine.getExtendedState().getVariables().put(CommonConstants.payTransition+order.getId(), 0);
            throw e;
        }
    }

    /**
     * 发货事件监听
     */
    @OnTransition(source = "WAIT_DELIVER", target = "WAIT_RECEIVE")
    @Transactional(rollbackFor = Exception.class)
    public void deliverTransition(Message<OrderStatusChangeEvent> message) {
        Order order = (Order) message.getHeaders().get(CommonConstants.orderHeader);
        AssertUtils.notNull(order, "发货，状态机订单信息异常");
        log.info("发货，状态机反馈信息：{}", message.getHeaders());
        try {
            //更新订单
            order.setStatus(OrderStatus.WAIT_RECEIVE.getKey());
            order.setUpdateTime(LocalDateTime.now());
            orderMapper.updateById(order);
            //成功 则为1
            orderStateMachine.getExtendedState().getVariables().put(CommonConstants.deliverTransition+order.getId(),1);
        } catch (Exception e) {
            //如果出现异常，则进行回滚
            log.error("deliverTransition 出现异常：{}",e.getMessage(),e);
            //将异常信息变量信息中，失败则为0
            orderStateMachine.getExtendedState().getVariables().put(CommonConstants.deliverTransition+order.getId(), 0);
            throw e;
        }
    }

    /**
     * 确认收货事件监听
     */
    @OnTransition(source = "WAIT_RECEIVE", target = "FINISH")
    @Transactional(rollbackFor = Exception.class)
    public void receiveTransition(Message<OrderStatusChangeEvent> message) {
        Order order = (Order) message.getHeaders().get(CommonConstants.orderHeader);
        AssertUtils.notNull(order, "确认收货，状态机订单信息异常");
        log.info("确认收货，状态机反馈信息：{}", message.getHeaders());
        try {
            //更新订单
            order.setStatus(OrderStatus.FINISH.getKey());
            order.setUpdateTime(LocalDateTime.now());
            orderMapper.updateById(order);
            //成功 则为1
            orderStateMachine.getExtendedState().getVariables().put(CommonConstants.receiveTransition+order.getId(),1);
        } catch (Exception e) {
            //如果出现异常，则进行回滚
            log.error("receiveTransition 出现异常：{}",e.getMessage(),e);
            //将异常信息变量信息中，失败则为0
            orderStateMachine.getExtendedState().getVariables().put(CommonConstants.receiveTransition+order.getId(), 0);
            throw e;
        }
    }

}
