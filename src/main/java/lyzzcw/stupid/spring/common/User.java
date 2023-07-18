package lyzzcw.stupid.spring.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/5 15:12
 * Description: No Description
 */
@Getter
@Setter
@ToString
public class User {
    private String name;

    public User(){
        System.out.println("user ---> 执行构造方法。。。");
    }

    public void init(){
        System.out.println("user ---> 执行初始化方法。。。");
    }

    public void destroy(){
        System.out.println("执行销毁方法。。。");
    }

}
