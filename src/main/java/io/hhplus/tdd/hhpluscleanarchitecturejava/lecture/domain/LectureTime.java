package io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain;

import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.instructure.entity.LectureTimeEntity;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
