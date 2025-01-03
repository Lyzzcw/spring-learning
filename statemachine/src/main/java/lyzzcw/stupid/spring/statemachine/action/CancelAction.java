package lyzzcw.stupid.spring.statemachine.action;

import lombok.extern.slf4j.Slf4j;
import lyzzcw.stupid.spring.statemachine.constants.Constant;
import lyzzcw.stupid.spring.statemachine.domain.Order;
import lyzzcw.stupid.spring.statemachine.enums.OrderState;
import lyzzcw.stupid.spring.statemachine.enums.OrderStateChangeEvent;
import lyzzcw.stupid.spring.statemachine.mapper.OrderMapper;
import lyzzcw.stupid.spring.statemachine.support.StateMachineManager;
import lyzzcw.work.component.common.utils.AssertUtil;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author Luiz
 * @version 1.0
 * Date: 2024/12/6 16:58
 * Description: No Description
 */
@Slf4j
public class CancelAction implements Action<OrderState, OrderStateChangeEvent> {

    @Resource
    private OrderMapper orderMapper;
    @Resource
    private StateMachineManager stateMachineManager;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void execute(StateContext<OrderState, OrderStateChangeEvent> stateContext) {
        Message<OrderStateChangeEvent> message = stateContext.getMessage();
        Order order = (Order) message.getHeaders().get(Constant.orderHeader);
        AssertUtil.notNull(order, "取消支付，状态机订单信息异常");
        log.info("取消支付,id/message -> {}/{}",stateMachineManager.getStateMachine().getId(),message);
        try {
            order.setStatus(OrderState.CLOSED.getKey());
            order.setUpdateTime(LocalDateTime.now());
            order.setDeleteFlag(1);
            orderMapper.updateById(order);
            //成功 则为1
            stateMachineManager.getStateMachine().getExtendedState().getVariables()
                    .put(Constant.cancelTransition+order.getId(),Constant.ORDER_STATE_RESULT_SUCCESS);
        } catch (Exception e) {
            //如果出现异常，则进行回滚
            log.error("cancelTransition 出现异常：{}",e.getMessage(),e);
            //将异常信息变量信息中，失败则为0
            stateMachineManager.getStateMachine().getExtendedState().getVariables()
                    .put(Constant.cancelTransition+order.getId(), Constant.ORDER_STATE_RESULT_FAILURE);
            throw e;
        }
    }
}
