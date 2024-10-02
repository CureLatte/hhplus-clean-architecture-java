package io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.interfaces;

import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.LectureTime;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@Data
@ToString
@NoArgsConstructor
public class GetLectureTimeResponse {
    private List<LectureTimeListDto> lectureTimeList;

    public GetLectureTimeResponse(List<LectureTimeListDto> lectureTimeList) {
        this.lectureTimeList = lectureTimeList;
    }
}
