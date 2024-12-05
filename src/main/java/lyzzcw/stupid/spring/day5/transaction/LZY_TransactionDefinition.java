package lyzzcw.stupid.spring.day5.transaction;

import org.springframework.lang.Nullable;
import org.springframework.transaction.TransactionDefinition;

/**
 * @author Luiz
 * @version 1.0
 * Date: 2024/12/5 9:14
 * Description:
 * 定义了与事务相关的方法，表示事务属性的常量等信息
 * 部分事务属性的常量与Propagation枚举类中的事务传播类型相对应
 */
public interface LZY_TransactionDefinition {
    /**
     * Spring当中默认的事务传播类型
     * 支持当前事务，若当前没有事务就创建一个新的事务
     * 如果已经存在一个事务，就加入这个事务
     */
    int PROPAGATION_REQUIRED = 0;

    /**
     *  如果当前存在事务，则加入该事务；如果当前没有事务，则以非事务的方式继续运行
     *  SUPPORTS事务传播类型表示支持当前事务，如果当前没有事务，就以非事务的方式执行。
     * 这里需要注意的是：外部不存在事务时，不会开启新的事务；外部存在事务时，加入到外部事务中。
     */
    int PROPAGATION_SUPPORTS = 1;

    /**
     *如果当前存在事务，则加入该事务；如果当前没有事务，则抛出异常
     * 这里，需要注意的是：这种事务传播类型具备强制性，
     * 当前操作必须存在事务，如果当前操作不存在事务，则抛出异常。
     */
    int PROPAGATION_MANDATORY = 2;

    /**
     *创建一个新的事务，如果当前存在事务，则把当前事务挂起
     *
     *REQUIRES_NEW事务传播类型表示，如果当前存在事务，则会把当前事务挂起，
     * 并重新创建新的事务执行，直到新的事务提交或者回滚，原来的事务才会恢复执行。
     * 这种事务传播类型具备隔离性，将原有事务和新创建的事务隔离，原有事务和新创建的事务的提交和回滚互不影响。
     *
     * 这里，需要注意的是：新创建的事务和被挂起的事务没有任何关系，它们是两个不相干的独立的事务。
     * 外部事务失败回滚，不会回滚内部事务的执行结果。内部事务失败抛出异常，被外部事务捕获到时，
     * 外部事务可以不处理内部事务的回滚操作。
     */
    int PROPAGATION_REQUIRES_NEW = 3;

    /**
     *  以非事务方式运行，如果当前存在事务，则把当前事务挂起
     */
    int PROPAGATION_NOT_SUPPORTED = 4;

    /**
     * 以非事务方式运行，如果当前存在事务，则抛出异常。
     *
     * 这里，需要注意的是：NEVER事务传播类型和NOT_SUPPORTED事务传播类型的区别是：
     * 如果当前存在事务，则NEVER事务传播类型会抛出异常，而NOT_SUPPORTED事务传播类型会把当前事务挂起，
     * 以非事务的方式执行。NEVER事务传播类型与MANDATORY事务传播类型的区别是：NEVER事务传播类型
     * 表示如果当前操作存在事务，则抛出异常，而MANDATORY事务传播类型表示如果当前操作不存在事务，则抛出异常。
     */
    int PROPAGATION_NEVER = 5;

    /**
     *  表示如果当前正有一个事务在运行中，则该方法运行在一个嵌套的事务中，
     被嵌套的事务可以独立于封装的事务进行提交或者回滚(这里需要事务的保存点)，
     如果封装的事务不存在,后续事务行为就跟 PROPAGATION_REQUIRES NEW 一样

     这里，需要注意的是：如果封装事务存在，并且外层事务抛出异常回滚，那么内层事务必须回滚；
     如果内层事务回滚，则并不影响外层事务的提交和回滚操作。
     如果封装事务不存在，则按照REQUIRED事务传播类型执行。
     */
    int PROPAGATION_NESTED = 6;

    /**
     *使用后端数据库默认的隔离级别
     *ISOLATION_DEFAULT隔离级别是Spring中PlatformTransactionManager默认的事务隔离级别
     */
    int ISOLATION_DEFAULT = -1;

    /**
     *最低的隔离级别
     * READ UNCOMMITTED
     * 一个事务A能够读取到另一个事务B未提交的数据
     */
    int ISOLATION_READ_UNCOMMITTED = 1;

    /**
     * 阻止脏读，但是可能会产生幻读或不可重复读的问题
     * READ COMMITTED
     */
    int ISOLATION_READ_COMMITTED = 2;

    /**
     *可以阻止脏读和不可重复读，但可能会发生幻读
     * REPEATABLE READ
     */
    int ISOLATION_REPEATABLE_READ = 4;

    /**
     *可以防止脏读、不可重复读以及幻读
     * SERIALIZABLE
     */
    int ISOLATION_SERIALIZABLE = 8;

    /**
     *使用默认的超时时间
     */
    int TIMEOUT_DEFAULT = -1;

    /**
     *获取事务的传播行为
     */
    default int getPropagationBehavior() {
        return PROPAGATION_REQUIRED;
    }

    /**
     *获取事务的隔离级别
     */
    default int getIsolationLevel() {
        return ISOLATION_DEFAULT;
    }

    /**
     *获取事务的超时时间
     */
    default int getTimeout() {
        return TIMEOUT_DEFAULT;
    }

    /**
     *返回当前是否为只读事务
     */
    default boolean isReadOnly() {
        return false;
    }

    /**
     *获取事务的名称
     */
    @Nullable
    default String getName() {
        return null;
    }

}
