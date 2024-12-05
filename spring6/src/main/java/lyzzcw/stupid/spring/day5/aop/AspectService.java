package lyzzcw.stupid.spring.day5.aop;

import org.springframework.stereotype.Component;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/18 10:31
 * Description: No Description
 */
@Component
public class AspectService {
    public void print(){
//        int i = 1/0;
        System.out.println("update aspect service");
    }
}
