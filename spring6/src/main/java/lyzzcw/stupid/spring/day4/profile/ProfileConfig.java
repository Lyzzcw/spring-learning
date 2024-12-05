package lyzzcw.stupid.spring.day4.profile;

import lyzzcw.stupid.spring.common.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/17 16:42
 * Description: No Description
 */
@Configuration
public class ProfileConfig {

    @Profile("dev")
    @Bean("dev-user")
    public User dev(){
        User user = new User();
        user.setName("dev-user");
        return user;
    }

    @Profile("test")
    @Bean("test-user")
    public User test(){
        User user = new User();
        user.setName("test-user");
        return user;
    }

    @Profile("prod")
    @Bean("prod-user")
    public User prod(){
        User user = new User();
        user.setName("prod-user");
        return user;
    }
}
