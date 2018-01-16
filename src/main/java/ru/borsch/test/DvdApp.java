package ru.borsch.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import ru.borsch.test.config.JpaConfiguration;

@Import(JpaConfiguration.class)
@SpringBootApplication(scanBasePackages={"ru.borsch.test"})
public class DvdApp {
    public static void main(String[] args) {
        SpringApplication.run(DvdApp.class, args);
    }
}
