package io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.interfaces;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HealthCheckResponse {
    private String message;

    public HealthCheckResponse(String message) {
        this.message = message;
    }
}
