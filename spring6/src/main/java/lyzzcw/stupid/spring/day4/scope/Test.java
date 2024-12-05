package lyzzcw.stupid.spring.day4.scope;


import lyzzcw.stupid.spring.common.Player;
import lyzzcw.stupid.spring.common.User;
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
                new AnnotationConfigApplicationContext(ScopeConfig.class);
        System.out.println("IOC容器启动完成。。。");
        String[] definitionNames = context.getBeanDefinitionNames();
        Arrays.stream(definitionNames).forEach(System.out::println);
        User user1  = context.getBean(User.class);
        System.out.println(user1);

        User user2 = context.getBean(User.class);
        System.out.println(user2);

        System.out.println(user1 == user2);

        System.out.println("----------------------------------------");
        Player player1  = context.getBean(Player.class);
        System.out.println(player1);

        Player player2 = context.getBean(Player.class);
        System.out.println(player2);

        System.out.println(player1 == player2);
        context.close();
    }
}
