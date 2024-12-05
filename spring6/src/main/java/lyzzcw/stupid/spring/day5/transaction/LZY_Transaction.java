package lyzzcw.stupid.spring.day5.transaction;

import org.springframework.aop.framework.ReflectiveMethodInvocation;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.lang.reflect.Method;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/18 14:59
 * Description: No Description
 */
public @interface LZY_Transaction {
    /**
     * {@link org.springframework.transaction.PlatformTransactionManager}
     *  事务管理器
     */

    /**
     * {@link org.springframework.transaction.TransactionDefinition}
     * 定义了事务相关的方法，事务属性的常量信息
     */

    /**
     * {@link org.springframework.transaction.TransactionStatus}
     * 存储事务的执行状态
     */

    /**
     * {@link org.springframework.transaction.TransactionExecution}
     * 处理事务的方法
     */

    /**
     * {@link org.springframework.transaction.SavepointManager}
     * 提供事务保存点操作
     */

    /**
     * {@link java.io.Flushable}
     * 刷新事务
     */


    /**
     * {@link org.springframework.transaction.annotation.EnableTransactionManagement}
     * 开启spring事务注解
     */

    /**
     * {@link org.springframework.transaction.interceptor.TransactionAspectSupport#invokeWithinTransaction(Method, Class, TransactionAspectSupport.InvocationCallback)}
     * 事务的核心逻辑，包括判断事务是否开启、目标方法执行、事务回滚、事务提交。
     */
}
