package io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.interfaces;

import lombok.Getter;

@Getter
public class ErrorResponseDto {
    String message;
    String code;


    public ErrorResponseDto(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
