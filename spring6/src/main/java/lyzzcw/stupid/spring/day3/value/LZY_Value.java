package lyzzcw.stupid.spring.day3.value;

import java.lang.annotation.*;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/14 9:46
 * Description: No Description
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LZY_Value {

    //指定要向bean的属性中注入的数据，数据可以是配置文件中的配置项，并且支持EL表达式
    String value();

}
