package lyzzcw.stupid.spring.day1.bean;

import lyzzcw.stupid.spring.common.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/6 15:32
 * Description: No Description
 */
@Configuration
@ComponentScan(basePackages = "lyzzcw.stupid.spring.day1.bean")
public class BeanConfig {

    @Bean(initMethod = "init",destroyMethod = "destroy")
    public Person person(){
        return new Person();
    }
}
