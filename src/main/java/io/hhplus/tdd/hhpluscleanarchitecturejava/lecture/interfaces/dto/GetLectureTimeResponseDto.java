package io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.interfaces.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@Data
@ToString
@NoArgsConstructor
public class GetLectureTimeResponseDto {
    private List<LectureTimeListDto> lectureTimeList;

    public GetLectureTimeResponseDto(List<LectureTimeListDto> lectureTimeList) {
        this.lectureTimeList = lectureTimeList;
    }
}
