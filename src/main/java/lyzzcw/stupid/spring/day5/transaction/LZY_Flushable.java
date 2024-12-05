package lyzzcw.stupid.spring.day5.transaction;

import java.io.IOException;

/**
 * @author Luiz
 * @version 1.0
 * Date: 2024/12/5 9:33
 * Description: 提供了刷新事务的方法
 */
public interface LZY_Flushable {
    /**
     * 刷新事务
     */
    void flush() throws IOException;
}
