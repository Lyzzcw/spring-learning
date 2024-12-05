package lyzzcw.stupid.spring.day5.transaction;

import org.springframework.transaction.TransactionException;

/**
 * @author Luiz
 * @version 1.0
 * Date: 2024/12/5 9:33
 * Description:提供了与保存点相关的事务处理操作的方法
 */
public interface LZY_SavepointManager {
    /**
     * 创建保存点
     */
    Object createSavepoint() throws TransactionException;
    /**
     * 将事务回滚到某个保存点
     */
    void rollbackToSavepoint(Object savepoint) throws TransactionException;
    /**
     * 释放某个保存点的事务
     */
    void releaseSavepoint(Object savepoint) throws TransactionException;
}
