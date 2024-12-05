package lyzzcw.stupid.spring.day2.dependson;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/13 15:47
 * Description: No Description
 */
@Configuration
public class DependsConfig {

    @Bean(name = "DependsOnClassB")
    @DependsOn("DependsOnClassA")
    public DependsOnClassB dependsOnClassB() {
        return new DependsOnClassB();
    }

    @Bean(name = "DependsOnClassA")
    public DependsOnClassA dependsOnClassA() {
        return new DependsOnClassA();
    }
}
