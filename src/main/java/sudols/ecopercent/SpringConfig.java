package sudols.ecopercent;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sudols.ecopercent.mapper.ItemMapper;
import sudols.ecopercent.mapper.UserMapper;

@Configuration
public class SpringConfig {

    @Bean
    public ItemMapper itemMapper() {
        return new ItemMapper();
    }

    @Bean
    public UserMapper userMapper() {
        return new UserMapper();
    }
}
