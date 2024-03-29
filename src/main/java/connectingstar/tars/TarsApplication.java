package connectingstar.tars;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TarsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TarsApplication.class, args);
	}

}
