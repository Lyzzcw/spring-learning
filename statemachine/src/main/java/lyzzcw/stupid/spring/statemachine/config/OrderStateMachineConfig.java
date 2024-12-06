package lyzzcw.stupid.spring.statemachine.config;

import lyzzcw.stupid.spring.statemachine.action.CancelAction;
import lyzzcw.stupid.spring.statemachine.enums.OrderStatus;
import lyzzcw.stupid.spring.statemachine.enums.OrderStatusChangeEvent;
import lyzzcw.stupid.spring.statemachine.guard.CancelGuard;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

/**
 * @author Luiz
 * @version 1.0
 * Date: 2024/12/6 10:25
 * Description: 订单状态机规则配置
 */
@Configuration
@EnableStateMachine(name = "orderStateMachine")
public class OrderStateMachineConfig extends StateMachineConfigurerAdapter<OrderStatus, OrderStatusChangeEvent> {

    /**
     * 配置状态
     */
    public void configure(StateMachineStateConfigurer<OrderStatus, OrderStatusChangeEvent> states) throws Exception {
        states.withStates()
                .initial(OrderStatus.WAIT_PAYMENT)
                .states(EnumSet.allOf(OrderStatus.class));
    }

    /**
     * 配置状态转换事件关系
     * 执行顺序 guard -> action -> event
     */
    public void configure(StateMachineTransitionConfigurer<OrderStatus, OrderStatusChangeEvent> transitions) throws Exception {
        transitions
                //取消支付事件:待支付-》已关闭
                .withExternal()
                .source(OrderStatus.WAIT_PAYMENT)
                .target(OrderStatus.CLOSED)
                .event(OrderStatusChangeEvent.CANCEL_PAYED)
                .guard(cancelGuard())
                .action(cancelAction())

                .and()
                //支付事件:待支付-》待发货
                .withExternal()
                .source(OrderStatus.WAIT_PAYMENT)
                .target(OrderStatus.WAIT_DELIVER)
                .event(OrderStatusChangeEvent.PAYED)

                //发货事件:待发货-》待收货
                .and()
                .withExternal()
                .source(OrderStatus.WAIT_DELIVER)
                .target(OrderStatus.WAIT_RECEIVE)
                .event(OrderStatusChangeEvent.DELIVERY)

                //收货事件:待收货-》已完成
                .and()
                .withExternal()
                .source(OrderStatus.WAIT_RECEIVE)
                .target(OrderStatus.FINISH)
                .event(OrderStatusChangeEvent.RECEIVED);
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
