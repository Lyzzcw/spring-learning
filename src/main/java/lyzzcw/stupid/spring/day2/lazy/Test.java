package lyzzcw.stupid.spring.day2.lazy;


import lyzzcw.stupid.spring.common.User;
import lyzzcw.stupid.spring.day2.Import.ImportConfig;
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
                new AnnotationConfigApplicationContext(LazyConfig.class);
        System.out.println("IOC容器启动完成。。。");
        String[] definitionNames = context.getBeanDefinitionNames();
        Arrays.stream(definitionNames).forEach(System.out::println); //此时user还没实例化

        User user1 = context.getBean(User.class);//此时开始加载user
        User user2 = context.getBean(User.class);

        System.out.println(user1 == user2);
        context.close();
    }
}
