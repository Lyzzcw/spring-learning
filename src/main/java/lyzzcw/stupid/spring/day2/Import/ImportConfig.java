package lyzzcw.stupid.spring.day2.Import;

import lyzzcw.stupid.spring.common.Person;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/13 14:29
 * Description: No Description
 */
@Import(value = {Person.class,CustomizeImportSelector.class
        ,CustomizeImportBeanDefinitionRegistrar.class})
@Configuration
public class ImportConfig {
}
