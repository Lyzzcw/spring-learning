package lyzzcw.stupid.spring.statemachine.constants;

/**
 * @author Luiz
 * @version 1.0
 * Date: 2024/12/6 13:40
 * Description: No Description
 */
public interface CommonConstants {
    String orderRedisPrefix = "order_statemachine_";
    String orderHeader = "order";
    String cancelTransition = "cancelTransition";
    String payTransition = "payTransition";
    String deliverTransition = "deliverTransition";
    String receiveTransition = "receiveTransition";
}
