package lyzzcw.stupid.spring.common;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/5 15:12
 * Description: No Description
 */
@Getter
@Setter
public class Person {
    private String name;

    public Person(){
        System.out.println("person ---> 执行构造方法。。。");
    }

    public void init(){
        System.out.println("person ---> 执行初始化方法。。。");
    }

    public void destroy(){
        System.out.println("执行销毁方法。。。");
    }

}
