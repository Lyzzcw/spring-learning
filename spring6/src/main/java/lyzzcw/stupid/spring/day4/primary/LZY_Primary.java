package lyzzcw.stupid.spring.day4.primary;

import java.lang.annotation.*;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/17 13:58
 * Description: No Description
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LZY_Primary {
    /**
     * 如果依赖的对象存在多个类型相同的Bean时，使用@Autowired注解已经无法
     * 正确完成Bean的装配工作。此时，可以使用@Qualifier注解明确指定要装配
     * 的对象。也可以使用@Primary注解优先装配对应的Bean对象。
     */
}
