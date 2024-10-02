package io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.interfaces.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class PostRegisterLectureResponseDto {
    private Boolean ok;
}
