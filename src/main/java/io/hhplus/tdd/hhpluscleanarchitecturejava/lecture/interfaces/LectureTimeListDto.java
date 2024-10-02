package io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.interfaces;

import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.Lecture;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.LectureTime;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LectureTimeListDto {


    private long lecture_time_id;
    private long lecture_id;
    private String title;
    private String lecturer;
    private LocalDate time;
    private long studentCnt;
    private boolean availYn;

    public LectureTimeListDto(LectureTime lectureTime) {
        this.lecture_time_id = lectureTime.getId();
        this.lecture_id = lectureTime.getLecture().getId();
        this.title = lectureTime.getLecture().getTitle();
        this.lecturer = lectureTime.getLecture().getLecturer();
        this.time = lectureTime.getTime();
        this.studentCnt = lectureTime.getStudentCnt();
        this.availYn = this.studentCnt < 30;
    }
}
