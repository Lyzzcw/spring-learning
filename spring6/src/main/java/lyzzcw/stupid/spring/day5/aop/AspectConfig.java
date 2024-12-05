package lyzzcw.stupid.spring.day5.aop;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/18 10:41
 * Description: No Description
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(basePackages = {"lyzzcw.stupid.spring.day5.aop"})
public class AspectConfig {

}
