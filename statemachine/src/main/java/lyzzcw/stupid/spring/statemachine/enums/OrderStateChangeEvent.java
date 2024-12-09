package lyzzcw.stupid.spring.statemachine.enums;

/**
 * @author Luiz
 * @version 1.0
 * Date: 2024/12/6 10:17
 * Description: No Description
 */
public enum OrderStateChangeEvent {
    //支付，发货，确认收货，取消支付
    PAYED, DELIVERY, RECEIVED,CANCEL_PAYED
    ;
}
