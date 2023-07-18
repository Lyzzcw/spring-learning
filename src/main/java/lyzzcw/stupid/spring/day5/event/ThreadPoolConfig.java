package lyzzcw.stupid.spring.day5.event;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/18 9:14
 * Description: No Description
 */
@Configuration
public class ThreadPoolConfig {
    @Bean("lyzzcw-stupid-executor")
    public Executor taskExecutor(){
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(20);
        taskExecutor.setCorePoolSize(8);
        taskExecutor.setQueueCapacity(50);
        taskExecutor.setKeepAliveSeconds(60);
        taskExecutor.setThreadNamePrefix("lyzzcw-stupid-executor");
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        //设置线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁
        //以确保应用最后能被关闭，而不是阻塞住
        taskExecutor.setAwaitTerminationSeconds(60);
        //修改拒绝策略
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //初始化
        taskExecutor.initialize();
        return taskExecutor;
    }
}
