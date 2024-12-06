package lyzzcw.stupid.spring.statemachine.enums;

/**
 * @author Luiz
 * @version 1.0
 * Date: 2024/12/6 9:04
 * Description: No Description
 */
public enum OrderStatus {

    WAIT_PAYMENT(1, "待支付"),
    WAIT_DELIVER(2, "待发货"),
    WAIT_RECEIVE(3, "待收货"),
    FINISH(4, "已完成"),
    CLOSED(-1, "已关闭")
    ;
    private final Integer key;
    private final String desc;

    OrderStatus(Integer key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public Integer getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }

    public static OrderStatus getByKey(Integer key) {
        for (OrderStatus e : values()) {
            if (e.getKey().equals(key)) {
                return e;
            }
        }
        throw new RuntimeException("enum not exists.");
    }
}
