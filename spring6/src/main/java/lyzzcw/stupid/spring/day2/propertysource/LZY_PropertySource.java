package lyzzcw.stupid.spring.day2.propertysource;


import org.springframework.core.io.support.PropertySourceFactory;

import java.lang.annotation.*;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/13 15:16
 * Description: No Description
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LZY_PropertySource {
    //资源加载名称
    String name() default "";
    //资源加载路径
    String[] value();
    //表示当配置文件未找到时，是否忽略文件未找到的错误
    boolean ignoreResourceNotFound() default false;
    //字符集编码
    String encoding() default "";
    //读取对应配置文件的工厂类
    Class<? extends PropertySourceFactory> factory() default PropertySourceFactory.class;
}
