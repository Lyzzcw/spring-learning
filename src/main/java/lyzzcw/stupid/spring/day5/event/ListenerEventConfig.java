package lyzzcw.stupid.spring.day5.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.task.TaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/18 9:26
 * Description: No Description
 */
@Configuration
@ComponentScan(basePackages = {"lyzzcw.stupid.spring.day5.event"})
public class ListenerEventConfig {
    @Autowired
    @Qualifier("lyzzcw-stupid-executor")
    private Executor taskExecutor;

    @Bean(name = AbstractApplicationContext.APPLICATION_EVENT_MULTICASTER_BEAN_NAME)
    public ApplicationEventMulticaster applicationEventMulticaster(){
        SimpleApplicationEventMulticaster simple =  new SimpleApplicationEventMulticaster();
        simple.setTaskExecutor(taskExecutor);
        return simple;
    }
}
