package lyzzcw.stupid.spring.statemachine.guard;

import lombok.extern.slf4j.Slf4j;
import lyzzcw.stupid.spring.statemachine.constants.Constant;
import lyzzcw.stupid.spring.statemachine.domain.Order;
import lyzzcw.stupid.spring.statemachine.enums.OrderState;
import lyzzcw.stupid.spring.statemachine.enums.OrderStateChangeEvent;
import lyzzcw.stupid.spring.statemachine.mapper.OrderMapper;
import lyzzcw.work.component.common.utils.AssertUtils;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;

import javax.annotation.Resource;

/**
 * @author Luiz
 * @version 1.0
 * Date: 2024/12/6 17:17
 * Description: No Description
 */
@Slf4j
public class CancelGuard implements Guard<OrderState, OrderStateChangeEvent> {
    @Resource
    private OrderMapper orderMapper;
    @Override
    public boolean evaluate(StateContext<OrderState, OrderStateChangeEvent> stateContext) {
        log.info("CancelGuard,id/message -> {}/{}",stateContext.getStateMachine().getId(),stateContext.getMessage());
        Order order = (Order) stateContext.getMessageHeaders().get(Constant.orderHeader);
        AssertUtils.notNull(order, "CancelGuard，订单信息异常");
        log.info("CancelGuard，state 反馈信息：{}", stateContext.getExtendedState().getVariables());
        Order order1 = orderMapper.selectById(order.getId());
        log.info("CancelGuard cancel again:{}", order1);
        if (order1 != null) {
            log.info("CancelGuard 调用成功，可执行取消");
            return true;
        }
        return false;
    }
}
