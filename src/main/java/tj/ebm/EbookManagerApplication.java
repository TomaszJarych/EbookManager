package tj.ebm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class EbookManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbookManagerApplication.class, args);
    }

}
