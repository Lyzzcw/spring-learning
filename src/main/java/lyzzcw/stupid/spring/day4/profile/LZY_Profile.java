package lyzzcw.stupid.spring.day4.profile;


import java.lang.annotation.*;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/17 16:35
 * Description: No Description
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
//@Conditional(ProfileCondition.class)
public @interface LZY_Profile {

    //指定环境的标识
    String[] value();
}
