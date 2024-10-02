package io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain;


import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.instructure.entity.RegisterLectureEntity;
import io.hhplus.tdd.hhpluscleanarchitecturejava.student.domain.Student;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class RegisterLecture {
    public long id;
    public Student student;
    public LectureTime lectureTime;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;


    public RegisterLecture(RegisterLectureEntity registerLectureEntity) {


        this.id = registerLectureEntity.getId();
        this.student = new Student(registerLectureEntity.getStudent());
        this.lectureTime = new LectureTime(registerLectureEntity.getLectureTime());
        this.createdAt = registerLectureEntity.getCreatedAt();
        this.updatedAt = registerLectureEntity.getUpdatedAt();
    }


}
