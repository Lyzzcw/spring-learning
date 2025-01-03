package lyzzcw.stupid.spring.statemachine.listener;

import lombok.extern.slf4j.Slf4j;
import lyzzcw.stupid.spring.statemachine.action.CancelAction;
import lyzzcw.stupid.spring.statemachine.constants.Constant;
import lyzzcw.stupid.spring.statemachine.domain.Order;
import lyzzcw.stupid.spring.statemachine.enums.OrderState;
import lyzzcw.stupid.spring.statemachine.enums.OrderStateChangeEvent;
import lyzzcw.stupid.spring.statemachine.mapper.OrderMapper;
import lyzzcw.stupid.spring.statemachine.support.StateContextHolder;
import lyzzcw.stupid.spring.statemachine.support.StateMachineManager;
import lyzzcw.work.component.common.utils.AssertUtil;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author Luiz
 * @version 1.0
 * Date: 2024/12/6 13:46
 * Description:
 * @WithStateMachine 会自动绑定到 Spring 容器管理的状态机实例，
 * 默认基于 StateMachineFactory 管理的单例状态机实例。
 * 如果通过 ThreadLocal 创建状态机实例，那么这些状态机实例并未被注册到 Spring 的上下文中。
 */
@Component
//@WithStateMachine
@Slf4j
public class OrderStateMachineHandler {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private StateMachineManager stateMachineManager;

    /**
     * 取消支付事件监听
     */
    @OnTransition(source = "WAIT_PAYMENT", target = "CLOSED")
    @Transactional(rollbackFor = Exception.class)
    public void cancelTransition() {
        /**
         * {@link CancelAction}
         */
    }

    /**
     * 支付事件监听
     */
    @OnTransition(source = "WAIT_PAYMENT", target = "WAIT_DELIVER")
    @Transactional(rollbackFor = Exception.class)
    public void payTransition() {
        Message<OrderStateChangeEvent> message = StateContextHolder.getStateContext().getMessage();
        Order order = (Order) message.getHeaders().get(Constant.orderHeader);
        AssertUtil.notNull(order, "支付，状态机订单信息异常");
        log.info("支付,id/message -> {}/{}",stateMachineManager.getStateMachine().getId(),message);
        try {
            order.setStatus(OrderState.WAIT_DELIVER.getKey());
            order.setUpdateTime(LocalDateTime.now());
            orderMapper.updateById(order);
            //成功 则为1
            stateMachineManager.getStateMachine().getExtendedState().getVariables()
                    .put(Constant.payTransition+order.getId(),Constant.ORDER_STATE_RESULT_SUCCESS);
        } catch (Exception e) {
            //如果出现异常，则进行回滚
            log.error("payTransition 出现异常：{}",e.getMessage(),e);
            //将异常信息变量信息中，失败则为0
            stateMachineManager.getStateMachine().getExtendedState().getVariables()
                    .put(Constant.payTransition+order.getId(), Constant.ORDER_STATE_RESULT_FAILURE);
            throw e;
        }
    }

    /**
     * 发货事件监听
     */
    @OnTransition(source = "WAIT_DELIVER", target = "WAIT_RECEIVE")
    @Transactional(rollbackFor = Exception.class)
    public void deliverTransition() {
        Message<OrderStateChangeEvent> message = StateContextHolder.getStateContext().getMessage();
        Order order = (Order) message.getHeaders().get(Constant.orderHeader);
        AssertUtil.notNull(order, "发货，状态机订单信息异常");
        log.info("发货,id/message -> {}/{}",stateMachineManager.getStateMachine().getId(),message);
        try {
            //更新订单
            order.setStatus(OrderState.WAIT_RECEIVE.getKey());
            order.setUpdateTime(LocalDateTime.now());
            orderMapper.updateById(order);
            //成功 则为1
            stateMachineManager.getStateMachine().getExtendedState().getVariables()
                    .put(Constant.deliverTransition+order.getId(),Constant.ORDER_STATE_RESULT_SUCCESS);
        } catch (Exception e) {
            //如果出现异常，则进行回滚
            log.error("deliverTransition 出现异常：{}",e.getMessage(),e);
            //将异常信息变量信息中，失败则为0
            stateMachineManager.getStateMachine().getExtendedState().getVariables()
                    .put(Constant.deliverTransition+order.getId(), Constant.ORDER_STATE_RESULT_FAILURE);
            throw e;
        }
    }

    /**
     * 确认收货事件监听
     */
    @OnTransition(source = "WAIT_RECEIVE", target = "FINISH")
    @Transactional(rollbackFor = Exception.class)
    public void receiveTransition() {
        Message<OrderStateChangeEvent> message = StateContextHolder.getStateContext().getMessage();
        Order order = (Order) message.getHeaders().get(Constant.orderHeader);
        AssertUtil.notNull(order, "确认收货，状态机订单信息异常");
        log.info("确认收货,id/message -> {}/{}",stateMachineManager.getStateMachine().getId(),message);
        try {
            //更新订单
            order.setStatus(OrderState.FINISH.getKey());
            order.setUpdateTime(LocalDateTime.now());
            orderMapper.updateById(order);
            //成功 则为1
            stateMachineManager.getStateMachine().getExtendedState().getVariables()
                    .put(Constant.receiveTransition+order.getId(),Constant.ORDER_STATE_RESULT_SUCCESS);
        } catch (Exception e) {
            //如果出现异常，则进行回滚
            log.error("receiveTransition 出现异常：{}",e.getMessage(),e);
            //将异常信息变量信息中，失败则为0
            stateMachineManager.getStateMachine().getExtendedState().getVariables()
                    .put(Constant.receiveTransition+order.getId(), Constant.ORDER_STATE_RESULT_FAILURE);
            throw e;
        }
    }

}
