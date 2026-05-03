package com.grocery.groceryapp;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication(
        exclude = {UserDetailsServiceAutoConfiguration.class}
)
public class GroceryappApplication {

    public static void main(String[] args) {
        SpringApplication.run(GroceryappApplication.class, args);
    }
}