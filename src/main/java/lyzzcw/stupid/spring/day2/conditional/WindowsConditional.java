package lyzzcw.stupid.spring.day2.conditional;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;


/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/13 16:45
 * Description: No Description
 */
public class WindowsConditional implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String osName = context.getEnvironment().getProperty("os.name");
        return osName.toLowerCase().contains("windows");
    }
}
