package io.hhplus.tdd.hhpluscleanarchitecturejava;

import org.springframework.boot.SpringApplication;

public class TestHhplusCleanArchitectureJavaApplication {

    public static void main(String[] args) {
        SpringApplication.from(HhplusCleanArchitectureJavaApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
