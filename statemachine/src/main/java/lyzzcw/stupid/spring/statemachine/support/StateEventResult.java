package lyzzcw.stupid.spring.statemachine.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Luiz
 * @version 1.0
 * Date: 2024/12/9 16:09
 * Description: No Description
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StateEventResult <S,E>{
    private boolean result;
    private S state;
    private E event;
    private String message;
}
