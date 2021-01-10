package htw.vs;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@org.springframework.boot.autoconfigure.SpringBootApplication
public class SpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(
                SpringBootApplication.class , args);
    }
}

