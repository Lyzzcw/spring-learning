package lyzzcw.stupid.spring.day4.classloading;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/17 15:58
 * Description: No Description
 */
@Configuration
public class ClassLoadingConfig {

    @Bean(initMethod = "init",destroyMethod = "destroy")
    public Loading loading(){
        return new Loading();
    }
}
