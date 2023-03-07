package sudols.ecopercent;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sudols.ecopercent.repository.ItemRepository;
import sudols.ecopercent.repository.jpa.JpaItemRepository;
import sudols.ecopercent.repository.jpa.JpaUserRepository;
import sudols.ecopercent.repository.UserRepository;
import sudols.ecopercent.service.ItemService;
import sudols.ecopercent.service.UserService;


@Configuration
public class SpringConfig {

    private EntityManager em;

    @Autowired
    public SpringConfig(EntityManager em) {
        this.em = em;
    }

    @Bean
    public UserService userService() {
        return new UserService(userRepository());
    }

    @Bean
    public UserRepository userRepository() {
        return new JpaUserRepository(em);
    }

    @Bean
    public ItemService itemService() {
        return new ItemService(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new JpaItemRepository(em);
    }
}
