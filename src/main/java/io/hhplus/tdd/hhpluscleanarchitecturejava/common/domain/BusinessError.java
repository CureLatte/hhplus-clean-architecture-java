package io.hhplus.tdd.hhpluscleanarchitecturejava.common.domain;

import lombok.Getter;

@Getter
public class BusinessError extends Exception {
    String message;

    public BusinessError(String message) {
        this.message = message;
    }
}
