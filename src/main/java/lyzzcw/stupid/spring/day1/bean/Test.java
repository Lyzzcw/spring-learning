package lyzzcw.stupid.spring.day1.bean;

import lyzzcw.stupid.spring.common.Person;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/6 15:36
 * Description: No Description
 */
public class Test {
    @org.junit.Test
    public void test() {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(BeanConfig.class);
        System.out.println("IOC容器启动完成。。。");

        Person person = context.getBean(Person.class);
        Person person1 = context.getBean(Person.class);

        System.out.println(person == person1);

        //拓展一下 @Configuration+@Bean  &  @Component+@Bean 的区别
        BeanConfig beanConfig = context.getBean(BeanConfig.class);
        Person person2 = beanConfig.person();
        System.out.println(person == person2);
        //@Configuration+@Bean  ---->  ture
        //@Component+@Bean  ---->  false
        //原因还是在于Configuration注解的 proxyBeanMethods 属性值为true，会被动态代理，该代理会拦截所有@Bean修饰的方法，会从容器中返回所需要的单例对象
        //而@Component则不会创建代理类，直接执行@Bean修饰的方法，返回一个新的对象
        context.close();

    }
}
