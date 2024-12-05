package lyzzcw.stupid.spring.day1.configuration;

import lyzzcw.stupid.spring.common.Person;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/5 15:19
 * Description: No Description
 */
public class Test {
    @org.junit.Test
    public void test1() {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(ConfigurationAnnotationConfig.class);
        ConfigurationAnnotationConfig config =
                context.getBean(ConfigurationAnnotationConfig.class);
        new Thread(()->{
            Person person1 = config.person();
            System.out.println(person1);
        }).start();
        new Thread(()->{
            Person person2 = config.person();
            System.out.println(person2);
        }).start();

    }

    @org.junit.Test
    public void test2() {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(ConfigurationAnnotationConfig2.class);
        ConfigurationAnnotationConfig2 config =
                context.getBean(ConfigurationAnnotationConfig2.class);
        Person person1 = config.person();
        Person person2 = config.person();
        System.out.println(person1 == person2); //false
    }
}
