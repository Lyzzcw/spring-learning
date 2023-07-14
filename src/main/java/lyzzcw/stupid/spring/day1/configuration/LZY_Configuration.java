package lyzzcw.stupid.spring.day1.configuration;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/5 14:51
 * Description: No Description
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface LZY_Configuration {

    /**
     * @Configuration 本质上是一个 @Component 注解
     * 被@Configuration 标注的配置类也会被注册到IOC容器中。
     * 同时，@Configuration 注解也会被 @ComponentScan扫描到
     */

    // 存入到Spring IOC 容器中的Bean的id
    @AliasFor(annotation = Component.class)
    String value() default "";

    // 表示被标记的配置类是否会被代理，并且在配置类中使用@Bean注解生成的Bean对象在IOC容器中的Bean对象是否是单例模式
    // true --> 会被代理，Bean对象为单例模式
    // false --> 不会被代理，每次都会返回一个新的Bean对象
    boolean proxyBeanMethods() default true;

    // 指定使用@Bean注解标注的方法是否需要具有唯一的方法名称
    boolean enforceUniqueMethods() default true;
}
