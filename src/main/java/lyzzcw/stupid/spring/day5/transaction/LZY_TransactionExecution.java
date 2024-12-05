package lyzzcw.stupid.spring.day5.transaction;

/**
 * @author Luiz
 * @version 1.0
 * Date: 2024/12/5 9:32
 * Description: 处理事务执行的方法
 */
public interface LZY_TransactionExecution {
    /**
     * 判断是否是新事务
     */
    boolean isNewTransaction();
    /**
     *设置为只回滚
     */
    void setRollbackOnly();
    /**
     *是否为只回滚
     */
    boolean isRollbackOnly();
    /**
     *判断当前事务是否已经完成
     */
    boolean isCompleted();
}
