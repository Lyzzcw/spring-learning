package lyzzcw.stupid.spring.day5.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/18 9:08
 * Description: No Description
 */
@Getter
public class ListenerEvent extends ApplicationEvent {

    public static final String EVENT_ASYNC = "async";
    public static final String EVENT_SYNC = "sync";

    private String name;

    public ListenerEvent(Object source,String name) {
        super(source);
        this.name = name;
    }
}
