package lyzzcw.stupid.spring.day4.primary;

import jakarta.annotation.Resource;
import lyzzcw.stupid.spring.day3.qualifier.QualifierDao;
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
public class PrimaryService {

    @Autowired
    private PrimaryDao primaryDao;

    @Resource
    @Qualifier("primaryDaoII")
    private PrimaryDao primaryDao1;

    public void apply() {
        this.primaryDao.apply();
    }

    public void apply1(){this.primaryDao1.apply();}
}
