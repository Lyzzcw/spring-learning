package lyzzcw.stupid.spring.day5.event;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/18 9:11
 * Description: No Description
 */
@Component
public class AsyncListenerEvent {

    @EventListener
    public void onApplicationEvent(ListenerEvent event) {
        if(event.getName().equals(ListenerEvent.EVENT_ASYNC)){
            System.out.println("异步事件监听器。。。");
        }
    }
}
