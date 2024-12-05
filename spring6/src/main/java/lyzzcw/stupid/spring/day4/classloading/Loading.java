package lyzzcw.stupid.spring.day4.classloading;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/17 15:55
 * Description: No Description
 */
public class Loading {
    public Loading(){
        System.out.println("1.执行Loading 的构造方法");
    }

    public void init(){
        System.out.println("3.执行Loading 的init方法");
    }

    @PostConstruct
    public void postConstruct(){
        System.out.println("2.执行Loading 的postConstruct方法");
    }

    @PreDestroy
    public void preDestroy(){
        System.out.println("4.执行Loading 的preDestroy方法");
    }

    public void destroy(){
        System.out.println("5.执行Loading 的destroy方法");
    }
}
