package lyzzcw.stupid.spring.day3.autowired;

import lyzzcw.stupid.spring.common.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/14 14:00
 * Description: No Description
 */
@Component
@ComponentScan(basePackages = {"lyzzcw.stupid.spring.day3.autowired"})
public class AutowiredConfig {

}
