package lyzzcw.stupid.spring.day3.autowired;


import lyzzcw.stupid.spring.common.User;
import lyzzcw.stupid.spring.day3.value.ValueConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


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
                new AnnotationConfigApplicationContext(AutowiredConfig.class);
        System.out.println("IOC容器启动完成。。。");
        AutowiredService autowiredService  = context.getBean(AutowiredService.class);
        System.out.println(autowiredService);
        AutowiredDao autowiredDao = context.getBean(AutowiredDao.class);
        System.out.println(autowiredDao);
        context.close();
    }
}
