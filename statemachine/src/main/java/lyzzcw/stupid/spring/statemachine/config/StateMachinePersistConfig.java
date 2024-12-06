package lyzzcw.stupid.spring.statemachine.config;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.data.redis.RedisStateMachineContextRepository;
import org.springframework.statemachine.data.redis.RedisStateMachinePersister;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.RepositoryStateMachinePersist;
import org.springframework.statemachine.persist.StateMachinePersister;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Luiz
 * @version 1.0
 * Date: 2024/12/6 10:30
 * Description: No Description
 */
@Configuration
@Slf4j
public class StateMachinePersistConfig <S, E>{
    @Resource
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * 持久化到内存map中
     */
    @Bean(name = "stateMachineMemPersister")
    @SuppressWarnings("all")
    public static StateMachinePersister getPersister() {
        return new DefaultStateMachinePersister(new StateMachinePersist() {
            private final Map<Object, StateMachineContext> map = new ConcurrentHashMap<>();
            @Override
            public void write(StateMachineContext context, Object contextObj) throws Exception {
                log.info("持久化状态机,context:{},contextObj:{}", JSON.toJSONString(context), JSON.toJSONString(contextObj));
                map.put(contextObj, context);
            }

            @Override
            public StateMachineContext read(Object contextObj) throws Exception {
                log.info("获取状态机,contextObj:{}", JSON.toJSONString(contextObj));
                StateMachineContext stateMachineContext = map.get(contextObj);
                log.info("获取状态机结果,stateMachineContext:{}", JSON.toJSONString(stateMachineContext));
                return stateMachineContext;
            }


        });
    }

    /**
     * 持久化到redis中，在分布式系统中使用
     *
     */
    @Bean(name = "stateMachineRedisPersister")
    public RedisStateMachinePersister<S, E> getRedisPersister() {
        RedisStateMachineContextRepository<S, E> repository = new RedisStateMachineContextRepository<>(redisConnectionFactory);
        RepositoryStateMachinePersist<S, E> p = new RepositoryStateMachinePersist<>(repository);
        return new RedisStateMachinePersister<>(p);
    }

}
