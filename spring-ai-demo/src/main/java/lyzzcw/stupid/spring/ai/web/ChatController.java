package lyzzcw.stupid.spring.ai.web;

import jakarta.annotation.Resource;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Luiz
 * @version 1.0
 * Date: 2024/12/30 14:50
 * Description: No Description
 */
@RestController
public class ChatController {
    @Resource
    private OpenAiChatClient openAiChatClient;
    @GetMapping("/chat")
    public Object chat(@RequestParam(value = "msg",defaultValue = "Hi") String msg){
        return openAiChatClient.call(msg);
    }
}
