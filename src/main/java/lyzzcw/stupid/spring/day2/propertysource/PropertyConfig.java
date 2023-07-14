package lyzzcw.stupid.spring.day2.propertysource;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/13 15:19
 * Description: No Description
 */
@Configuration
@PropertySource(value = "classpath:day2/test.properties")
public class PropertyConfig {
}
