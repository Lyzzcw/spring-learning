package lyzzcw.stupid.spring.day4.scope;

import lyzzcw.stupid.spring.common.Player;
import lyzzcw.stupid.spring.common.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/17 15:00
 * Description: No Description
 */
@Configuration
public class ScopeConfig {

    @Bean
    @Scope(value = "prototype")
    public User user(){
        return new User();
    }

    @Bean
    @Scope(value = "singleton")
    public Player player(){
        return new Player();
    }
}
