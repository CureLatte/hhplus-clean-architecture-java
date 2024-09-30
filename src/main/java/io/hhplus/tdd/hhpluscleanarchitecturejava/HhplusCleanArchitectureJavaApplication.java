package io.hhplus.tdd.hhpluscleanarchitecturejava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class HhplusCleanArchitectureJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(HhplusCleanArchitectureJavaApplication.class, args);
    }

}
