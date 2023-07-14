package lyzzcw.stupid.spring.day3.resource;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/14 15:07
 * Description: No Description
 */
@Component
public class ResourceService {

    @Resource(name = "ResourceDaoI")
    private ResourceDao resourceDao;

    public void apply() {
        this.resourceDao.apply();
    }
}
