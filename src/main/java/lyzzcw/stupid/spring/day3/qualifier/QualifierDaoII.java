package lyzzcw.stupid.spring.day3.qualifier;

import org.springframework.stereotype.Component;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/14 15:04
 * Description: No Description
 */
@Component(value = "QualifierDaoII")
public class QualifierDaoII implements QualifierDao{
    @Override
    public void apply() {
        System.out.println("QualifierDaoII.apply");
    }
}
