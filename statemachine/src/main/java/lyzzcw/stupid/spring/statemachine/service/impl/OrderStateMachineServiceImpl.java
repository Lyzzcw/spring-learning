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
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
            stateMachineManager.stop();
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

    @Override
    public void exportToUml() {
        StateMachine<OrderState, OrderStateChangeEvent> stateMachine = stateMachineManager.empty();
        String fileName = "order_statemachine.puml";
        try {
            // 确定输出目录为项目根目录同级的 doc 文件夹
            Path docDirectory = Paths.get("doc");
            if (!Files.exists(docDirectory)) {
                Files.createDirectories(docDirectory);
            }

            // 生成 UML 文件路径
            File outputFile = new File(docDirectory.toFile(), fileName);

            // 写入 UML 文件内容
            try (FileWriter writer = new FileWriter(outputFile)) {
                writer.write("@startuml\n");

                // 导出所有状态
                stateMachine.getStates().forEach(state -> {
                    try {
                        writer.write(state.getId().name() + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                // 导出所有状态转移
                stateMachine.getTransitions().forEach(transition -> {
                    try {
                        State<OrderState, OrderStateChangeEvent> source = transition.getSource();
                        State<OrderState, OrderStateChangeEvent> target = transition.getTarget();
                        if (source != null && target != null) {
                            String event = transition.getTrigger().getEvent().name();
                            writer.write(source.getId().name() + " --> " + target.getId().name() + " : " + event + "\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                writer.write("@enduml\n");
                log.info("UML file exported to: " + outputFile.getAbsolutePath());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            stateMachineManager.stop();
        }
    }
}
