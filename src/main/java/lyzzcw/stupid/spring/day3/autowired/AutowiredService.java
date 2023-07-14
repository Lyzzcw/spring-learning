package lyzzcw.stupid.spring.day3.autowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/14 14:52
 * Description: No Description
 */
@Component
public class AutowiredService {
    @Autowired
    private AutowiredDao autowiredDao;
}
