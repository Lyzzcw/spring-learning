package lyzzcw.stupid.spring.statemachine.action;

import lombok.extern.slf4j.Slf4j;
import lyzzcw.stupid.spring.statemachine.constants.CommonConstants;
import lyzzcw.stupid.spring.statemachine.domain.Order;
import lyzzcw.stupid.spring.statemachine.enums.OrderStatus;
import lyzzcw.stupid.spring.statemachine.enums.OrderStatusChangeEvent;
import lyzzcw.stupid.spring.statemachine.mapper.OrderMapper;
import lyzzcw.work.component.common.utils.AssertUtils;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

import javax.annotation.Resource;

/**
 * @author Luiz
 * @version 1.0
 * Date: 2024/12/6 16:58
 * Description: No Description
 */
@Slf4j
public class CancelAction implements Action<OrderStatus, OrderStatusChangeEvent> {

    @Override
    public void execute(StateContext<OrderStatus, OrderStatusChangeEvent> stateContext) {
        log.info("CancelAction，message 反馈信息：{}", stateContext.getMessage());
        Order order = (Order) stateContext.getMessageHeaders().get(CommonConstants.orderHeader);
        AssertUtils.notNull(order, "CancelAction，订单信息异常");
        log.info("CancelAction，state 反馈信息：{}", stateContext.getExtendedState().getVariables());

        log.info("CancelAction 调用成功，放回购物车");
    }
}
