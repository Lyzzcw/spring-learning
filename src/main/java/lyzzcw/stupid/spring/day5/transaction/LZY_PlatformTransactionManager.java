package lyzzcw.stupid.spring.day5.transaction;

import org.springframework.lang.Nullable;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.TransactionStatus;

/**
 * @author Luiz
 * @version 1.0
 * Date: 2024/12/5 9:12
 * Description:
 * 通过PlatformTransactionManager接口
 * Spring为Hibernate、MyBatis或者JTA等持久化框架提供了事务管理器
 * 但是具体的实现还是要各自的框架自己完成
 */
public interface LZY_PlatformTransactionManager extends TransactionManager {
    /**
     *获取事务状态
     */
    TransactionStatus getTransaction(@Nullable TransactionDefinition definition) throws TransactionException;
    /**
     *提交事务
     */
    void commit(TransactionStatus status) throws TransactionException;
    /**
     *回滚事务
     */
    void rollback(TransactionStatus status) throws TransactionException;
}
