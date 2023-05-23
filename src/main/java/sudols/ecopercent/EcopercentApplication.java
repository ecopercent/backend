package sudols.ecopercent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class EcopercentApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcopercentApplication.class, args);
	}

}
