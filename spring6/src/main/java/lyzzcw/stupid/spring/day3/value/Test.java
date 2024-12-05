package lyzzcw.stupid.spring.day3.value;


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
                new AnnotationConfigApplicationContext(ValueConfig.class);
        System.out.println("IOC容器启动完成。。。");
        ValueConfig config = context.getBean(ValueConfig.class);
        System.out.println(config);
        context.close();
    }
}
