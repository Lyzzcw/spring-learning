package lyzzcw.stupid.spring.day4.profile;


import lyzzcw.stupid.spring.common.User;
import lyzzcw.stupid.spring.day4.primary.PrimaryConfig;
import lyzzcw.stupid.spring.day4.primary.PrimaryService;
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
                new AnnotationConfigApplicationContext();
        System.out.println("IOC容器启动完成。。。");

        context.getEnvironment().setActiveProfiles("test");
        context.register(ProfileConfig.class);
        context.refresh();

        User user = context.getBean(User.class);
        System.out.println(user);
        context.close();
    }
}
