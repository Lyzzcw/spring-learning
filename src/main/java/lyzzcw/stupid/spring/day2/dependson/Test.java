package lyzzcw.stupid.spring.day2.dependson;


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
                new AnnotationConfigApplicationContext(DependsConfig.class);
        System.out.println("IOC容器启动完成。。。");
        context.close();
    }
}
