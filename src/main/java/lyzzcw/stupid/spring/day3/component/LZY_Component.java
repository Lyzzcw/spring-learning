package lyzzcw.stupid.spring.day3.component;

import java.lang.annotation.*;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/14 8:57
 * Description: No Description
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LZY_Component {

    /**
     * The value may indicate a suggestion for a logical component name,
     * to be turned into a Spring bean in case of an autodetected component.
     * @return the suggested component name, if any (or empty String otherwise)
     */
    // 用于指定注入bean的id.如果没有指定，则默认为当前class的名称
    String value() default "";
}
