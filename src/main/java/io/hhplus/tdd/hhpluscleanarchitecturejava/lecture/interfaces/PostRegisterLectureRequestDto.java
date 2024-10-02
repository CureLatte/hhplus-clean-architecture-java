package io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.interfaces;


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
