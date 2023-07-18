package lyzzcw.stupid.spring.day5.event;


import lyzzcw.stupid.spring.day4.scope.ScopeConfig;
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
                new AnnotationConfigApplicationContext(ListenerEventConfig.class);
        System.out.println("IOC容器启动完成。。。");
        context.publishEvent(new ListenerEvent(new Test(),ListenerEvent.EVENT_ASYNC));
        context.close();
    }
}
