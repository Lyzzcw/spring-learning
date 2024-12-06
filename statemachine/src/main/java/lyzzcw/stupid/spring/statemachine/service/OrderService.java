package lyzzcw.stupid.spring.statemachine.service;

import lyzzcw.stupid.spring.statemachine.domain.Order;

/**
 * @author Luiz
 * @version 1.0
 * Date: 2024/12/6 10:50
 * Description: No Description
 */
public interface OrderService {
    Order confirm(Order order);

    void pay(Long id);

    void cancel(Long id);

    void deliver(Long id);

    void receive(Long id);

    Order getById(Long id);
}
