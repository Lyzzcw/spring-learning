package lyzzcw.stupid.spring.statemachine.support;

import lombok.extern.slf4j.Slf4j;
import lyzzcw.stupid.spring.statemachine.enums.OrderState;
import lyzzcw.stupid.spring.statemachine.enums.OrderStateChangeEvent;
import lyzzcw.stupid.spring.statemachine.listener.OrderStateListener;
import lyzzcw.work.component.common.utils.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Component;

/**
 * @author Luiz
 * @version 1.0
 * Date: 2024/12/7 16:51
 * Description: No Description
 */
@Component
@Slf4j
public class StateMachineManager {

    // 线程 本地环境
    private final ThreadLocal<StateMachine<OrderState, OrderStateChangeEvent>>
            LocalStateMachine = new InheritableThreadLocal<>();

    @Autowired
    private StateMachineFactory<OrderState, OrderStateChangeEvent> orderStateMachineFactory;

    @Autowired
    private StateMachinePersist<OrderState, OrderStateChangeEvent, String> repositoryStateMachinePersist;

    @Autowired
    private OrderStateListener orderStateListener;
    public void initialize(String contextId) throws Exception {
        // 创建新的状态机实例
        StateMachine<OrderState, OrderStateChangeEvent> stateMachine =
                orderStateMachineFactory.getStateMachine();

        //绑定监听器
        stateMachine.addStateListener(orderStateListener);
        LocalStateMachine.set(stateMachine);
        this.restore(contextId);
    }

    public StateMachine<OrderState, OrderStateChangeEvent> empty() {
        // 创建新的状态机实例
        StateMachine<OrderState, OrderStateChangeEvent> stateMachine =
                orderStateMachineFactory.getStateMachine();
        LocalStateMachine.set(stateMachine);
        return LocalStateMachine.get();
    }

    public StateMachine<OrderState, OrderStateChangeEvent> getStateMachine() {
        return LocalStateMachine.get();
    }

    /**
     * 从redis恢复当前状态机状态
     *
     * @param contextId 上下文 ID
     * @throws Exception 例外
     */
    public void restore(String contextId) throws Exception {
        StateMachine<OrderState, OrderStateChangeEvent> stateMachine = LocalStateMachine.get();
        AssertUtil.notNull(stateMachine, "must init state machine first");
        final StateMachineContext<OrderState, OrderStateChangeEvent> context =
                repositoryStateMachinePersist.read(contextId);
        stateMachine.stopReactively().block();
        stateMachine.getStateMachineAccessor().doWithAllRegions(function -> function.resetStateMachineReactively(context).block());
        stateMachine.startReactively().block();
    }

    /**
     * 在redis持久化当前状态机状态
     *
     * @param contextId 上下文 ID
     * @throws Exception 例外
     */
    public void persist(String contextId) throws Exception {
        StateMachine<OrderState, OrderStateChangeEvent> stateMachine = LocalStateMachine.get();
        AssertUtil.notNull(stateMachine, "must init state machine first");
        repositoryStateMachinePersist.write(StateMachineUtils.buildStateMachineContext(stateMachine), contextId);
        this.stop();
    }

    public void stop(){
        LocalStateMachine.get().stopReactively().subscribe();
        this.clear();
    }
    public void clear() {
        LocalStateMachine.remove();
        StateContextHolder.clear();
    }
}
