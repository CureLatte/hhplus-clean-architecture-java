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


    public LectureTime(LectureTimeEntity lectureTimeEntity) {
        this.id = lectureTimeEntity.getId();
        this.lectureId = lectureTimeEntity.getLecture().getId();
        this.studentCnt = lectureTimeEntity.getStudentCnt();
        this.time = lectureTimeEntity.getTime();
        this.createdAt = lectureTimeEntity.getCreatedAt();
        this.updatedAt = lectureTimeEntity.getUpdatedAt();
        this.lecture = new Lecture(lectureTimeEntity.getLecture());
    }


    public LectureTimeEntity toEntity() {
        LectureTimeEntity lectureTimeEntity = new LectureTimeEntity();
        lectureTimeEntity.setStudentCnt(studentCnt);
        lectureTimeEntity.setTime(time);
        lectureTimeEntity.setId(id);
        lectureTimeEntity.setCreatedAt(createdAt);
        lectureTimeEntity.setUpdatedAt(updatedAt);
        lectureTimeEntity.setLecture(lecture.toEntity());
        return lectureTimeEntity;
    }

}
