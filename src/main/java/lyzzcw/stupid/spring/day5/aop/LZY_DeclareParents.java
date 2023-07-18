package lyzzcw.stupid.spring.day5.aop;

import org.aspectj.lang.annotation.DeclareParents;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/18 14:17
 * Description: No Description
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface LZY_DeclareParents {
    /**
     * 可以为被增强的类实现新的接口，并且可以添加新的方法
     */

    //指定目标类型的表达式，如果在全类名的后面添加+，则表示的是当前类及其子类
    String value();

    //指定方法或者子类的默认实现类
    Class defaultImpl() default DeclareParents.class;
}
