package lyzzcw.stupid.spring.statemachine.service.impl;

import lombok.extern.slf4j.Slf4j;
import lyzzcw.stupid.spring.statemachine.constants.Constant;
import lyzzcw.stupid.spring.statemachine.enums.OrderState;
import lyzzcw.stupid.spring.statemachine.enums.OrderStateChangeEvent;
import lyzzcw.stupid.spring.statemachine.service.OrderStateMachineService;
import lyzzcw.stupid.spring.statemachine.support.StateMachineManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.access.StateMachineAccessor;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Luiz
 * @version 1.0
 * Date: 2024/12/7 14:25
 * Description: No Description
 */
@Service
@Slf4j
public class OrderStateMachineServiceImpl implements OrderStateMachineService {

    @Resource
    private StateMachineManager stateMachineManager;
    @Autowired
    private StateMachinePersist<OrderState, OrderStateChangeEvent, String> repositoryStateMachinePersist;

    @Override
    public OrderState read(String id) {
        try {
            stateMachineManager.initialize(Constant.orderRedisPrefix.concat(id));
            StateMachine<OrderState, OrderStateChangeEvent> orderStateMachine
                    = stateMachineManager.getStateMachine();
            log.info("State machine ,{}, current state :{}",id,orderStateMachine.getState());
            return orderStateMachine.getState().getId();
        }catch (Exception e) {
            log.error("Error restored :{}",id,e);
            throw new RuntimeException(e);
        }finally {
            stateMachineManager.clear();
        }
    }

    @Override
    public void write(String id, OrderState orderState) {
        try {
            stateMachineManager.initialize(Constant.orderRedisPrefix.concat(id));
            StateMachine<OrderState, OrderStateChangeEvent> stateMachine
                    = stateMachineManager.getStateMachine();
            // 获取状态机的访问器
            StateMachineAccessor<OrderState, OrderStateChangeEvent> accessor = stateMachine.getStateMachineAccessor();
            // 强制设置状态
            accessor.doWithAllRegions(access -> {
                access.resetStateMachineReactively(new DefaultStateMachineContext<>(
                        orderState,  // 强制设置为目标状态
                        null,             // 没有事件
                        null,             // 没有扩展信息
                        null              // 不修改状态历史记录
                )).block();
            });
            stateMachineManager.persist(Constant.orderRedisPrefix.concat(id));
            log.info("State machine ,{}, current state :{}",id,stateMachine.getState());
        }catch (Exception e) {
            log.error("Error restored :{}",id,e);
        }finally {
            stateMachineManager.clear();
        }
    }
}
