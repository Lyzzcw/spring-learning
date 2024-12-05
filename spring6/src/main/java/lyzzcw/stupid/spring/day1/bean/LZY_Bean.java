package lyzzcw.stupid.spring.day1.bean;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/6 15:22
 * Description: No Description
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LZY_Bean {
    //在使用spring注解开发的时候，如果是我们自己写的类，可以在类上标注@Component等将类注入到IOC中
    //但是我们在依赖第三库的时候，就只能通过@Bean注解将其注入到IOC容器中

    //同value 指定注入到IOC的Bean的名称，默认是方法名称
    @AliasFor("name")
    String[] value() default {};

    //同name 指定注入到IOC的Bean的名称，默认是方法名称
    @AliasFor("value")
    String[] name() default {};

    //是否支持自动按照类型注入到其他的Bean中
    //此属性会影响@Autowired 不会影响@Resource
    boolean autowireCandidate() default true;

    //指定初始化的方法
    String initMethod() default "";

    //指定销毁的方法
    String destroyMethod() default AbstractBeanDefinition.INFER_METHOD;

}
