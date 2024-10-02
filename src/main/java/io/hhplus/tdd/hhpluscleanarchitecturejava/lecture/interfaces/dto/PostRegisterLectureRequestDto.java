package io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.interfaces.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class PostRegisterLectureRequestDto {
    private long studentId;
    private long lectureTimeId;

}
