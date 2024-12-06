package lyzzcw.stupid.spring.statemachine.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Luiz
 * @version 1.0
 * Date: 2024/12/6 10:42
 * Description: No Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order {
    /** id */
    private Long id;
    /** 订单编号 */
    private String orderCode;
    /** 订单状态 */
    private int status;
    /** 订单名称 */
    private String name;
    /** 订单价格 */
    private BigDecimal price;
    /** 删除标记 */
    private int deleteFlag;
    /** 创建时间 */
    private LocalDateTime createTime;
    /** 更新时间 */
    private LocalDateTime updateTime;
    /** 创建人 */
    private String createUserCode;
    /** 更新人 */
    private String updateUserCode;
    /** 版本号 */
    private int version;
    /** 备注 */
    private String remark;
}
