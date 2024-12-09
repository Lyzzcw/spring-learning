package lyzzcw.stupid.spring.statemachine.service;

import lyzzcw.stupid.spring.statemachine.enums.OrderState;

/**
 * @author Luiz
 * @version 1.0
 * Date: 2024/12/7 14:23
 * Description: No Description
 */
public interface OrderStateMachineService {
    OrderState read(String id);
    void write(String id, OrderState orderState);
}
