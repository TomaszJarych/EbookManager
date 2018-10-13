package tj.ebm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EbookManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EbookManagerApplication.class, args);
	}

	//TODO add ratings to BookEntity;
}
