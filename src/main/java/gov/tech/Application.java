package gov.tech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = {"gov.tech"})
@ComponentScan("gov.tech")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}