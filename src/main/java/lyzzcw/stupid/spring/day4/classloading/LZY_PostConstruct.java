package lyzzcw.stupid.spring.day4.classloading;

import java.lang.annotation.*;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/17 15:51
 * Description: No Description
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LZY_PostConstruct {
}
