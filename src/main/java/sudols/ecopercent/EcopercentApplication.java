package sudols.ecopercent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;

@SpringBootApplication
public class EcopercentApplication {

	public static void main(String[] args) {
		dbConnection db = new dbConnection();
		db.conn("postgres", "jji", "1234");
		SpringApplication.run(EcopercentApplication.class, args);
	}

}
