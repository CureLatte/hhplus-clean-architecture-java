package io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.vo;

import lombok.Getter;

@Getter
public class BusinessError  extends Exception {
    String message;

    public BusinessError(String message){
        this.message = message;
    }
}
