package bg.softuni.hotelagency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class HotelagencyApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelagencyApplication.class, args);
    }

}
