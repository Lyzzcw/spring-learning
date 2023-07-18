package lyzzcw.stupid.spring.day4.classloading;


import lyzzcw.stupid.spring.day3.qualifier.QualifierConfig;
import lyzzcw.stupid.spring.day3.qualifier.QualifierService;
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
                new AnnotationConfigApplicationContext(ClassLoadingConfig.class);
        System.out.println("IOC容器启动完成。。。");
        String[] definitionNames = context.getBeanDefinitionNames();
        Arrays.stream(definitionNames).forEach(System.out::println);
        Loading loading  = context.getBean(Loading.class);

        System.out.println(loading);

        context.close();
    }
}
