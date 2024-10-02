package io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.interfaces;

import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.LectureTime;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.RegisterLecture;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class GetRegisterLectureHistoryResponseDto {
    private List<RegisterLectureListDto> lectureHistory;

}
