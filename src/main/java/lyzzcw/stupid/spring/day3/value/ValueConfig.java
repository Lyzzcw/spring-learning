package lyzzcw.stupid.spring.day3.value;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/14 9:52
 * Description: No Description
 */
@Component
@PropertySource(value = "classpath:/day3/test.properties")
@Data
public class ValueConfig {
    //注入普通字符串
    @Value("normal string")
    private String normalString;

    //注入系统操作属性
    @Value("#{systemProperties['os.name']}")
    private String systemProperty;

    //注入表达式的信息
    @Value("#{T(java.lang.Math).random * 100.0}")
    private double random;

    //引入配置文件，设置默认值
    @Value("${author.name:lzy}")
    private String authorName;

    //混合使用
    @Value("#{'${server.name}'.split(',')}")
    private List<String> serverNames;

    @Value("#{'redis://'.concat('${spring.redis.host}').concat(':').concat('${spring.redis.port}')}")
    private String redisAddress;

}
