package lyzzcw.stupid.spring.day2.Import;

import lyzzcw.stupid.spring.common.Player;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/13 14:42
 * Description: No Description
 */
public class CustomizeImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        String beanName = Player.class.getName();
        BeanDefinition beanDefinition = new RootBeanDefinition(Player.class);
        registry.registerBeanDefinition(beanName, beanDefinition);
        System.out.println("CustomizeImportBeanDefinitionRegistrar");
    }
}
