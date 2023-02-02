package com.hoaxify.hoaxifyspringboot;

import com.hoaxify.hoaxifyspringboot.api.entities.User;
import com.hoaxify.hoaxifyspringboot.api.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;



@SpringBootApplication
@EnableSwagger2
public class HoaxifySpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(HoaxifySpringBootApplication.class, args);
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hoaxify.hoaxifyspringboot"))
                .build();
    }

    @Bean
    CommandLineRunner createSuperUser(UserService userService) {
        return args -> {
            User su = new User();
            su.setUsername("yemreyolcu");
            su.setDisplayName("admin");
            su.setPassword("Yunus.123");
            userService.createUser(su);
        };
    }

}
