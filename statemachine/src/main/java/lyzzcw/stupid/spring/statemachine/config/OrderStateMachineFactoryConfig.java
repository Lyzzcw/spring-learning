package lyzzcw.stupid.spring.statemachine.config;

import lyzzcw.stupid.spring.statemachine.action.CancelAction;
import lyzzcw.stupid.spring.statemachine.enums.OrderState;
import lyzzcw.stupid.spring.statemachine.enums.OrderStateChangeEvent;
import lyzzcw.stupid.spring.statemachine.guard.CancelGuard;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;


/**
 * 订单状态机工厂配置
 * ThreadLocal + Factory 解决状态机并发问题
 * @author Luiz
 * @date 2024/12/09
 */
@Configuration
@EnableStateMachineFactory(name = "orderStateMachineFactory")
public class OrderStateMachineFactoryConfig extends StateMachineConfigurerAdapter<OrderState, OrderStateChangeEvent> {

    /**
     * 配置设置
     */
    public void configure(StateMachineConfigurationConfigurer<OrderState, OrderStateChangeEvent> config) throws Exception {
        config.withConfiguration()
                .machineId("orderStateMachine");

    }
    /**
     * 配置状态
     */
    public void configure(StateMachineStateConfigurer<OrderState, OrderStateChangeEvent> states) throws Exception {
        states.withStates()
                .initial(OrderState.WAIT_PAYMENT)
                .states(EnumSet.allOf(OrderState.class));
    }

    /**
     * 配置状态转换事件关系
     * 执行顺序 guard -> action -> event
     */
    public void configure(StateMachineTransitionConfigurer<OrderState, OrderStateChangeEvent> transitions) throws Exception {
        transitions
                //取消支付事件:待支付-》已关闭
                .withExternal()
                .source(OrderState.WAIT_PAYMENT)
                .target(OrderState.CLOSED)
                .event(OrderStateChangeEvent.CANCEL_PAYED)
                .guard(cancelGuard())
                .action(cancelAction())

                .and()
                //支付事件:待支付-》待发货
                .withExternal()
                .source(OrderState.WAIT_PAYMENT)
                .target(OrderState.WAIT_DELIVER)
                .event(OrderStateChangeEvent.PAYED)

                //发货事件:待发货-》待收货
                .and()
                .withExternal()
                .source(OrderState.WAIT_DELIVER)
                .target(OrderState.WAIT_RECEIVE)
                .event(OrderStateChangeEvent.DELIVERY)

                //收货事件:待收货-》已完成
                .and()
                .withExternal()
                .source(OrderState.WAIT_RECEIVE)
                .target(OrderState.FINISH)
                .event(OrderStateChangeEvent.RECEIVED);
    }

    @Bean
    public CancelGuard cancelGuard(){
        return new CancelGuard();
    }
    @Bean
    public CancelAction cancelAction(){
        return new CancelAction();
    }
}
