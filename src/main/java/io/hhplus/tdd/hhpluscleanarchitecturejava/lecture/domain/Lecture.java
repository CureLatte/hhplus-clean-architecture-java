package io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain;


import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.instructure.entity.LectureEntity;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@Data
@AllArgsConstructor
public class Lecture {
    public long id;
    public String title;
    public String lecturer;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;


    public Lecture(LectureEntity lectureEntity) {
        this.id = lectureEntity.getId();
        this.title = lectureEntity.getTitle();
        this.lecturer = lectureEntity.getLecturer();
        this.createdAt = lectureEntity.getCreatedAt();
        this.updatedAt = lectureEntity.getUpdatedAt();
    }
}
