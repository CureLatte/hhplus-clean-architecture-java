package io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain;

import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.instructure.LectureEntity;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.instructure.LectureTimeEntity;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Builder
@Data
@AllArgsConstructor
public class LectureTime {
    public long id;
    public long lectureId;
    public long studentCnt;
    public LocalDate time;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
    public Lecture lecture;

//    public LectureTime(long id, long lectureId, long studentCnt, LocalDate time, LocalDateTime createdAt, LocalDateTime updatedAt, Lecture lecture) {
//        this.id = id;
//        this.lectureId = lectureId;
//        this.studentCnt = studentCnt;
//        this.time = time;
//        this.createdAt = createdAt;
//        this.updatedAt = updatedAt;
//        this.lecture = lecture;
//    }

    public LectureTime(LectureTimeEntity lectureTimeEntity) {
        this.id = lectureTimeEntity.getId();
        this.lectureId = lectureTimeEntity.getLecture().getId();
        this.studentCnt = lectureTimeEntity.getStudentCnt();
        this.time = lectureTimeEntity.getTime();
        this.createdAt = lectureTimeEntity.getCreatedAt();
        this.updatedAt = lectureTimeEntity.getUpdatedAt();
        this.lecture = new Lecture(lectureTimeEntity.getLecture());
    }
}
