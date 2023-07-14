package lyzzcw.stupid.spring.day2.lazy;

import java.lang.annotation.*;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/13 17:38
 * Description: No Description
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LZY_Lazy {

    //对于单例Bean来说，如果不想IOC容器启动的时候就创建Bean对象，而是在第一次使用时创建Bean对象
    //就可以使用@Lazy注解
    //@Lazy注解只对单例Bean起作用，如果使用@Scope注解指定多实例Bean，则@Lazy注解将失效

    boolean value() default true;
}
