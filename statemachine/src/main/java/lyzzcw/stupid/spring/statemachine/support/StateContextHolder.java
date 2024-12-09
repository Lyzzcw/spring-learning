package lyzzcw.stupid.spring.statemachine.support;

import lyzzcw.stupid.spring.statemachine.enums.OrderState;
import lyzzcw.stupid.spring.statemachine.enums.OrderStateChangeEvent;
import org.springframework.statemachine.StateContext;
/**
 * @author Luiz
 * @version 1.0
 * Date: 2024/12/7 16:51
 * Description: No Description
 */
public class StateContextHolder {

    // 线程 本地环境
    private static final ThreadLocal<StateContext<OrderState, OrderStateChangeEvent>>
            LocalStateMachineContext = new InheritableThreadLocal<>();

    public static StateContext<OrderState, OrderStateChangeEvent> getStateContext() {
        return LocalStateMachineContext.get();
    }

    public static void setStateContext(StateContext<OrderState, OrderStateChangeEvent> context) {
         LocalStateMachineContext.set(context);
    }

    public static void clear(){
        LocalStateMachineContext.remove();
    }
}
