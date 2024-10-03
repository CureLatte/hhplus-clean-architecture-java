package io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.entity;

import io.hhplus.tdd.hhpluscleanarchitecturejava.common.domain.BusinessError;
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


    final long MAX_REGISTER_STUDENT = 30;
    final String NOT_FOUNT_LECTURE_ERROR_MESSAGE = "강의 내역이 존재하지 않습니다.";
    final String MAX_REGISTER_STUDENT_ERROR_MESSAGE = "최대 신청 인원을 초과하였습니다.";


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


    public void registerValidate() throws BusinessError {
        if (this.lecture == null) {
            throw new BusinessError(this.NOT_FOUNT_LECTURE_ERROR_MESSAGE);
        }

        if (this.studentCnt >= this.MAX_REGISTER_STUDENT) {
            throw new BusinessError(this.MAX_REGISTER_STUDENT_ERROR_MESSAGE);
        }
    }
}
