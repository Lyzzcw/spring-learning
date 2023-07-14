package lyzzcw.stupid.spring.day3.autowired;

import java.lang.annotation.*;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/14 13:55
 * Description: No Description
 */
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LZY_Autowired {
    //按照类型注入

    //FIXME 与@Autowired 注解用法相同的还有 @Inject注解（JSR330规范实现的，没有required属性）
    //表示是否必须注入成功，设置为true时，注入失败会报错，false则不会
    boolean required() default true;
}
