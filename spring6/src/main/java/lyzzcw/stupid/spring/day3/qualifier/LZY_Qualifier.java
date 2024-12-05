package lyzzcw.stupid.spring.day3.qualifier;

import java.lang.annotation.*;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/14 15:01
 * Description: No Description
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface LZY_Qualifier {

    //表示当前bean的唯一标识。
    String value() default "";

}
