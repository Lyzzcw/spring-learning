package lyzzcw.stupid.spring.day3.resource;

import jakarta.annotation.Resource;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/14 15:19
 * Description: No Description
 */
public @interface LZY_Resource {

    //Resource注解是JSR250规范中提供的注解，如果使用JDK低于8高于11，则需要额外引入依赖
    //Resource注解默认是通过ByName的方式装配Bean的，找不到Bean的话，就通过ByType的方式
    //@Resource 其实等同于 @Autowired+@Quality

    //指定装配名称的bean
    String name() default "";
    //引用指向资源的名称
    String lookup() default "";
    //装订指定类型的bean
    Class<?> type() default Object.class;
    //指定身份验证类型
    Resource.AuthenticationType authenticationType() default Resource.AuthenticationType.CONTAINER;
    //是否可以共享
    boolean shareable() default true;
    //指定资源的映射名称
    String mappedName() default "";
    //描述
    String description() default "";

    public static enum AuthenticationType {
        CONTAINER,
        APPLICATION;

        private AuthenticationType() {
        }
    }

}
