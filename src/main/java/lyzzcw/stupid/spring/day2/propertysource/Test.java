package lyzzcw.stupid.spring.day2.propertysource;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/13 15:22
 * Description: No Description
 */
public class Test {

    @org.junit.Test
    public void test() {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(PropertyConfig.class);
        System.out.println("IOC容器启动完成。。。");

        ConfigurableEnvironment configurableEnvironment = context.getEnvironment();

        System.out.println(configurableEnvironment.getProperty("name"));
        System.out.println(configurableEnvironment.getProperty("age"));

        context.close();
    }
}
