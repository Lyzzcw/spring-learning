package lyzzcw.stupid.spring.statemachine;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lyzzcw.work.component.common.http.okhttp.OkHttpUtils;

import java.util.concurrent.*;

/**
 * @author Luiz
 * @version 1.0
 * Date: 2024/12/10 8:57
 * Description: No Description
 */
public class Test {
    public static final ExecutorService testExecutor = new ThreadPoolExecutor(
            10,
            20,
            1000*60, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(1000)
    );

    @org.junit.Test
    public void test() throws ExecutionException, InterruptedException {
        for (int i = 0; i < 50 ; i++){
            Callable<String> callable = () -> OkHttpUtils.builder().url("http://127.0.0.1:8901/order/deliver")
                    // 有参数的话添加参数，可多个
                    .addParam("id", "35")
                    // 也可以添加多个
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .get()
                    // 可选择是同步请求还是异步请求
                    //.async();
                    .sync();
            Future<String> future = testExecutor.submit(callable);
            String result = future.get();
            JSONObject jsonObject = JSON.parseObject(result);
            System.out.println(jsonObject);
        }
        testExecutor.shutdown();
    }
}
