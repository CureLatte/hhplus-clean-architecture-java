package io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.interfaces.dto;

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
