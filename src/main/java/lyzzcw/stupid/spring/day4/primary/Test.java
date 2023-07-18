package lyzzcw.stupid.spring.day4.primary;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;


/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/13 14:29
 * Description: No Description
 */
public class Test {
    @org.junit.Test
    public void test() {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(PrimaryConfig.class);
        System.out.println("IOC容器启动完成。。。");
        String[] definitionNames = context.getBeanDefinitionNames();
        Arrays.stream(definitionNames).forEach(System.out::println);
        PrimaryService autowiredService  = context.getBean(PrimaryService.class);

        System.out.println(autowiredService);
        autowiredService.apply();

        context.close();
    }
}
