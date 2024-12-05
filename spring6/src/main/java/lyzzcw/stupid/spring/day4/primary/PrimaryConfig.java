package lyzzcw.stupid.spring.day4.primary;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/14 15:06
 * Description: No Description
 */
@Configuration
@ComponentScan(basePackages = "lyzzcw.stupid.spring.day4.primary")
public class PrimaryConfig {

    @Bean
    @Primary
    public PrimaryDao primaryDaoI(){
        return new PrimaryDaoI();
    }

    @Bean
    public PrimaryDao primaryDaoII(){
        return new PrimaryDaoII();
    }
}
