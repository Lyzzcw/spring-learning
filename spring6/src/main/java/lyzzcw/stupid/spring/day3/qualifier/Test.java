package lyzzcw.stupid.spring.day3.qualifier;


import lyzzcw.stupid.spring.day3.autowired.AutowiredConfig;
import lyzzcw.stupid.spring.day3.autowired.AutowiredDao;
import lyzzcw.stupid.spring.day3.autowired.AutowiredService;
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
                new AnnotationConfigApplicationContext(QualifierConfig.class);
        System.out.println("IOC容器启动完成。。。");
        String[] definitionNames = context.getBeanDefinitionNames();
        Arrays.stream(definitionNames).forEach(System.out::println);
        QualifierService autowiredService  = context.getBean(QualifierService.class);

        System.out.println(autowiredService);
        autowiredService.apply();

        context.close();
    }
}
