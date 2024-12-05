package lyzzcw.stupid.spring.day4.scope;

import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/17 14:49
 * Description: No Description
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LZY_Scope {

    //value 表示作用域范围
    //singleton 单例bean，IOC容器启动时，就会创建Bean对象，如果标注了@Lazy,则会在第一次获取Bean实例时，创建Bean对象
    //prototype 原型bean，每次从IOC容器获取Bean对象时，都会创建一个新的对象，@Lazy对原型Bean不起作用
    //request 表示作用域是当前请求范围
    //session 表示作用域是当前回话范围
    //application 表示作用域是当前应用范围
    @AliasFor("scopeName")
    String value() default "";

    //同 value
    @AliasFor("value")
    String scopeName() default "";

    //指定Bean对象的代理方式
    //DEFAULT  等同于 NO
    //NO 不使用代理
    //INTERFACES JDK基于接口代理
    //TARGET_CLASS CGLIB基于目标类的子类创建代理对象
    ScopedProxyMode proxyMode() default ScopedProxyMode.DEFAULT;

}
