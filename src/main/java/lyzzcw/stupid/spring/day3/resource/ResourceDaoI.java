package lyzzcw.stupid.spring.day3.resource;

import org.springframework.stereotype.Component;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/14 15:04
 * Description: No Description
 */
@Component(value = "ResourceDaoI")
public class ResourceDaoI implements ResourceDao {
    @Override
    public void apply() {
        System.out.println("ResourceDaoI.apply");
    }
}
