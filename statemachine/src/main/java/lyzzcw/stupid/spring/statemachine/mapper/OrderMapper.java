package lyzzcw.stupid.spring.statemachine.mapper;

import lyzzcw.stupid.spring.statemachine.domain.Order;

/**
 * @author Luiz
 * @version 1.0
 * Date: 2024/12/6 10:41
 * Description: No Description
 */
public interface OrderMapper {
    int insert(Order order);

    Order selectById(Long id);

    int updateById(Order id);
}
