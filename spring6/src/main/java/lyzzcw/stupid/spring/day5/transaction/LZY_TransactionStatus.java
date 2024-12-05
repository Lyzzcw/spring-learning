package lyzzcw.stupid.spring.day5.transaction;

import org.springframework.transaction.SavepointManager;
import org.springframework.transaction.TransactionExecution;

import java.io.Flushable;

/**
 * @author Luiz
 * @version 1.0
 * Date: 2024/12/5 9:29
 * Description: TransactionStatus接口主要用来存储事务执行的状态
 */
public interface LZY_TransactionStatus extends
        LZY_TransactionExecution,
        LZY_SavepointManager,
        LZY_Flushable {
    /**
     *是否有保存点
     */
    boolean hasSavepoint();
    /**
     * 刷新
     */
    void flush();
}
