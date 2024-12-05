package lyzzcw.stupid.spring.day2.conditional;

import org.springframework.context.annotation.Condition;

import java.lang.annotation.*;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/13 16:30
 * Description: No Description
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LZY_Conditional {

    /**
     * All {@link Condition} classes that must {@linkplain Condition#matches match}
     * in order for the component to be registered.
     */
    //指定实现Conditional接口的实现类
    Class<? extends Condition>[] value();

}
