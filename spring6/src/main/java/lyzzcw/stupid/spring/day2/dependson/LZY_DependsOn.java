package lyzzcw.stupid.spring.day2.dependson;

import java.lang.annotation.*;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/13 15:35
 * Description: No Description
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LZY_DependsOn {

    //表示Bean的唯一标识，被指定的Bean会在Spring创建当前Bean之前被创建
    String[] value() default {};
}
