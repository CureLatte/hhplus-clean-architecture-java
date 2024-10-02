package io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.interfaces;


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
