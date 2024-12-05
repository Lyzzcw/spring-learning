package lyzzcw.stupid.spring.day5.transaction;

import org.springframework.aot.hint.annotation.Reflective;
import org.springframework.core.annotation.AliasFor;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;

import java.lang.annotation.*;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/18 15:38
 * Description: No Description
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Reflective
public @interface LZY_Transactional {

    //指定事务管理器
    @AliasFor("transactionManager")
    String value() default "";

    //同value
    @AliasFor("value")
    String transactionManager() default "";

    //设置属性
    String[] label() default {};

    //指定事务的传播方式
    Propagation propagation() default Propagation.REQUIRED;

    //指定事务的隔离级别
    Isolation isolation() default Isolation.DEFAULT;

    //事务的超时时间
    int timeout() default TransactionDefinition.TIMEOUT_DEFAULT;

    //以string类型设置超时时间
    String timeoutString() default "";

    //指定是否为只读事务
    boolean readOnly() default false;

    //指定异常类
    Class<? extends Throwable>[] rollbackFor() default {};

    //指定异常类的全类名
    String[] rollbackForClassName() default {};

    //指定不回滚的异常类
    Class<? extends Throwable>[] noRollbackFor() default {};

    //指定不回滚异常类的全类名
    String[] noRollbackForClassName() default {};
}
