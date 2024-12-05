package lyzzcw.stupid.spring.day2.conditional;

import lyzzcw.stupid.spring.common.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/13 16:40
 * Description: No Description
 */
@Configuration
public class ConditionalConfig {

    @Bean
    @Conditional(value = {MacConditional.class})
    public Person macPerson() {
        Person person = new Person();
        person.setName("mac");
        return person;
    }

    @Bean
    @Conditional(value = {WindowsConditional.class})
    public Person windowsPerson() {
        Person person = new Person();
        person.setName("windows");
        return person;
    }

    @Bean
    @Conditional(value = {LinuxConditional.class})
    public Person linuxPerson() {
        Person person = new Person();
        person.setName("linux");
        return person;
    }
}
