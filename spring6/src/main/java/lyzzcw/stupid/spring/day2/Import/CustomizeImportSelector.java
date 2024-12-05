package lyzzcw.stupid.spring.day2.Import;

import lyzzcw.stupid.spring.common.Person;
import lyzzcw.stupid.spring.common.User;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.function.Predicate;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/13 14:35
 * Description: No Description
 */
public class CustomizeImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{User.class.getName(), Person.class.getName()};
    }

    @Override
    public Predicate<String> getExclusionFilter() {
        return ImportSelector.super.getExclusionFilter();
    }
}
