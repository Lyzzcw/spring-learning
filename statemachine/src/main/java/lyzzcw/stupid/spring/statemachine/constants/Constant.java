package lyzzcw.stupid.spring.statemachine.constants;

/**
 * @author Luiz
 * @version 1.0
 * Date: 2024/12/6 13:40
 * Description: No Description
 */
public interface Constant {
    String orderRedisPrefix = "order_statemachine_";
    String orderRedisLockPrefix = "order_statemachine_lock_";
    String orderHeader = "order";
    String cancelTransition = "cancelTransition";
    String payTransition = "payTransition";
    String deliverTransition = "deliverTransition";
    String receiveTransition = "receiveTransition";

    Integer ORDER_STATE_RESULT_FAILURE = 0;
    Integer ORDER_STATE_RESULT_SUCCESS = 1;
}
