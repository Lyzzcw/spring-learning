package lyzzcw.stupid.spring.day1.componentscan;

import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/6 14:51
 * Description: No Description
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
//@Repeatable(ComponentScans.class)
public @interface LZY_ComponentScan {

    //同 value属性，指定扫描的包名
    @AliasFor("basePackages")
    String[] value() default {};

    //同 basePackages属性，指定扫描的包名
    @AliasFor("value")
    String[] basePackages() default {};

    //指定要扫描的类
    Class<?>[] basePackageClasses() default {};

    //指定向IOC注入Bean对象时的命名规则
    Class<? extends BeanNameGenerator> nameGenerator() default BeanNameGenerator.class;

    //扫描类时，用于处理并转换符合条件的Bean的作用范围
    Class<? extends ScopeMetadataResolver> scopeResolver() default AnnotationScopeMetadataResolver.class;

    //默认使用的代理方式
    ScopedProxyMode scopedProxy() default ScopedProxyMode.DEFAULT;

    //用于指定扫描的文件类型，默认是扫描指定包下的 **/*.clase
    String resourcePattern() default "**/*.class";/*ClassPathScanningCandidateComponentProvider.DEFAULT_RESOURCE_PATTERN */;

    //是否自动检测
    boolean useDefaultFilters() default true;

    //自定义扫描过滤规则
    ComponentScan.Filter[] includeFilters() default {};

    //自定义扫描过滤规则
    ComponentScan.Filter[] excludeFilters() default {};

    //是否开启懒加载
    boolean lazyInit() default false;


    @Retention(RetentionPolicy.RUNTIME)
    @Target({})
    @interface Filter {

        FilterType type() default FilterType.ANNOTATION;


        @AliasFor("classes")
        Class<?>[] value() default {};


        @AliasFor("value")
        Class<?>[] classes() default {};


        String[] pattern() default {};

    }
}
