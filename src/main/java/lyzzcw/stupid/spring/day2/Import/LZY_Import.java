package lyzzcw.stupid.spring.day2.Import;

import org.springframework.context.annotation.Configuration;

import java.lang.annotation.*;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/13 14:01
 * Description: No Description
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LZY_Import {

    //@Import 注解可以将第三方包中的类注入到IOC容器中，只能注解到类或其他注解上
    /**
     * {@link Configuration @Configuration}, {@link ImportSelector},
     * {@link ImportBeanDefinitionRegistrar}, or regular component classes to import.
     */

    // value : Class数组类型，必须是普通类，必须实现了ImportSelector接口的类和实现了ImportBeanDefinitionRegistrar的类
    Class<?>[] value();
}
