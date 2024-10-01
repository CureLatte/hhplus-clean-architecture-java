package io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain;

import lombok.Getter;

@Getter
public class ErrorResponse {
    String message;
    String code;


    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
