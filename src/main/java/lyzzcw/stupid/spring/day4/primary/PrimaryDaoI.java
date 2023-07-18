package lyzzcw.stupid.spring.day4.primary;

import org.springframework.stereotype.Component;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/14 15:04
 * Description: No Description
 */
@Component
public class PrimaryDaoI implements PrimaryDao {
    @Override
    public void apply() {
        System.out.println("PrimaryDaoI.apply");
    }
}
