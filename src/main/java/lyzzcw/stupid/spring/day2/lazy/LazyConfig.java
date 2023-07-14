package lyzzcw.stupid.spring.day2.lazy;

import lyzzcw.stupid.spring.common.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/13 17:44
 * Description: No Description
 */
@Configuration
public class LazyConfig {
    @Bean
    @Lazy
    public User user(){
        return new User();
    }
}
