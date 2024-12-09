package lyzzcw.stupid.spring.statemachine.support;

import org.springframework.statemachine.ExtendedState;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.region.Region;
import org.springframework.statemachine.state.AbstractState;
import org.springframework.statemachine.state.HistoryPseudoState;
import org.springframework.statemachine.state.PseudoState;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.AbstractStateMachine;
import org.springframework.statemachine.support.DefaultExtendedState;
import org.springframework.statemachine.support.DefaultStateMachineContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Luiz
 * @version 1.0
 * Date: 2024/12/9 9:10
 * Description: No Description
 */
public class StateMachineUtils{
    public static <S,E> StateMachineContext<S, E> buildStateMachineContext(StateMachine<S, E> stateMachine) {
        ExtendedState extendedState = new DefaultExtendedState();
        extendedState.getVariables().putAll(stateMachine.getExtendedState().getVariables());

        ArrayList<StateMachineContext<S, E>> childs = new ArrayList<StateMachineContext<S, E>>();
        S id = null;
        State<S, E> state = stateMachine.getState();
        if (state.isSubmachineState()) {
            StateMachine<S, E> submachine = ((AbstractState<S, E>) state).getSubmachine();
            id = submachine.getState().getId();
            childs.add(buildStateMachineContext(submachine));
        } else if (state.isOrthogonal()) {
            Collection<Region<S, E>> regions = ((AbstractState<S, E>)state).getRegions();
            for (Region<S, E> r : regions) {
                StateMachine<S, E> rsm = (StateMachine<S, E>) r;
                childs.add(buildStateMachineContext(rsm));
            }
            id = state.getId();
        } else {
            id = state.getId();
        }

        // building history state mappings
        Map<S, S> historyStates = new HashMap<S, S>();
        PseudoState<S, E> historyState = ((AbstractStateMachine<S, E>) stateMachine).getHistoryState();
        if (historyState != null && ((HistoryPseudoState<S, E>)historyState).getState() != null) {
            historyStates.put(null, ((HistoryPseudoState<S, E>) historyState).getState().getId());
        }
        Collection<State<S, E>> states = stateMachine.getStates();
        for (State<S, E> ss : states) {
            if (ss.isSubmachineState()) {
                StateMachine<S, E> submachine = ((AbstractState<S, E>) ss).getSubmachine();
                PseudoState<S, E> ps = ((AbstractStateMachine<S, E>) submachine).getHistoryState();
                if (ps != null) {
                    State<S, E> pss = ((HistoryPseudoState<S, E>)ps).getState();
                    if (pss != null) {
                        historyStates.put(ss.getId(), pss.getId());
                    }
                }
            }
        }
        return new DefaultStateMachineContext<S, E>(childs, id, null, null, extendedState, historyStates, stateMachine.getId());
    }
}
