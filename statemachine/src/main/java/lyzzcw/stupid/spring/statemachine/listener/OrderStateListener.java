package lyzzcw.stupid.spring.statemachine.listener;

import lombok.extern.slf4j.Slf4j;
import lyzzcw.stupid.spring.statemachine.action.CancelAction;
import lyzzcw.stupid.spring.statemachine.enums.OrderState;
import lyzzcw.stupid.spring.statemachine.enums.OrderStateChangeEvent;
import lyzzcw.stupid.spring.statemachine.support.StateContextHolder;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Luiz
 * @version 1.0
 * Date: 2024/12/6 13:46
 * Description: 订单事件监听器
 */
@Component
@Slf4j
public class OrderStateListener
        implements StateMachineListener<OrderState, OrderStateChangeEvent> {

    @Resource
    private OrderStateMachineHandler orderStateMachineHandler;

    @Override
    public void stateChanged(State<OrderState, OrderStateChangeEvent> from, State<OrderState, OrderStateChangeEvent> to) {
        log.info("State changed from {} to {}", from != null ? from.getId() : "null", to.getId());
        // 根据目标状态处理业务逻辑
        switch (to.getId()) {
            case CLOSED:
                /**
                 * {@link CancelAction}
                 */
//                orderStateMachineHandler.cancelTransition();
                break;
            case WAIT_DELIVER:
                orderStateMachineHandler.payTransition();
                break;
            case WAIT_RECEIVE:
                orderStateMachineHandler.deliverTransition();
                break;
            case FINISH:
                orderStateMachineHandler.receiveTransition();
                break;
            default:
                break;
        }
    }

    @Override
    public void stateEntered(State<OrderState, OrderStateChangeEvent> state) {

    }

    @Override
    public void stateExited(State<OrderState, OrderStateChangeEvent> state) {

    }

    @Override
    public void eventNotAccepted(Message<OrderStateChangeEvent> event) {
        log.warn("Event not accepted: {}", event.getPayload());
    }

    @Override
    public void transition(Transition<OrderState, OrderStateChangeEvent> transition) {

    }

    @Override
    public void transitionStarted(Transition<OrderState, OrderStateChangeEvent> transition) {
        log.info("Transition started: From {} to {}",
                transition.getSource() != null ? transition.getSource().getId() : "null",
                transition.getTarget() != null ? transition.getTarget().getId() : "null");
    }

    @Override
    public void transitionEnded(Transition<OrderState, OrderStateChangeEvent> transition) {

    }

    @Override
    public void stateMachineStarted(StateMachine<OrderState, OrderStateChangeEvent> stateMachine) {
        log.info("State Machine started {}->{}",Thread.currentThread().getName(),stateMachine);
    }

    @Override
    public void stateMachineStopped(StateMachine<OrderState, OrderStateChangeEvent> stateMachine) {
        log.info("State Machine stopped {}->{}",Thread.currentThread().getName(),stateMachine);
    }

    @Override
    public void stateMachineError(StateMachine<OrderState, OrderStateChangeEvent> stateMachine, Exception exception) {
        log.error("State machine error in ID {}: {}", stateMachine.getId(), exception.getMessage(), exception);
    }

    @Override
    public void extendedStateChanged(Object key, Object value) {

    }

    @Override
    public void stateContext(StateContext<OrderState, OrderStateChangeEvent> stateContext) {
        if(log.isDebugEnabled()){
            log.debug("update state context: {}", stateContext);
        }
        StateContextHolder.setStateContext(stateContext);
    }
}
