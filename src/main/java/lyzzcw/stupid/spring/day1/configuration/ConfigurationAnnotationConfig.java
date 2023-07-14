package lyzzcw.stupid.spring.day1.configuration;

import lyzzcw.stupid.spring.common.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/5 15:13
 * Description: No Description
 */
@Configuration
public class ConfigurationAnnotationConfig {
    @Bean
    public Person person() {
        return new Person();
    }
}
