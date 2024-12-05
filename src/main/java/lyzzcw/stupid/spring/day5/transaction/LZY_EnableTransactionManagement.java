package lyzzcw.stupid.spring.day5.transaction;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.transaction.annotation.TransactionManagementConfigurationSelector;

import java.lang.annotation.*;

/**
 * @author Luiz
 * @version 1.0
 * Date: 2024/12/5 10:01
 * Description: No Description
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(TransactionManagementConfigurationSelector.class)
public @interface LZY_EnableTransactionManagement {
    /**
     * 表示指定目标类代理还是指定接口代理
     * true：指定目标类代理，此时会使用CGLib代理，false：指定接口代理，此时会使用JDK代理
     */
    boolean proxyTargetClass() default false;

    /**
     * AdviceMode枚举类型的属性，表示事务通知是如何执行的。取值为PROXY或者ASPECTJ，
     * PROXY：事务会通过代理的方式执行
     * ASPECTJ：事务会通过aspectj的方式执行。
     * 如果是同一个类中调用的话，可以指定为ASPECTJ
     */
    AdviceMode mode() default AdviceMode.PROXY;

    /**
     * 事务处理的执行顺序
     */
    int order() default Ordered.LOWEST_PRECEDENCE;
}
