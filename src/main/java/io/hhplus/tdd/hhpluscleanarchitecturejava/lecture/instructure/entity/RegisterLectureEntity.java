package io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.instructure.entity;


import io.hhplus.tdd.hhpluscleanarchitecturejava.student.instructure.StudentEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "register_lecture")
public class RegisterLectureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "studnet_id")
    private StudentEntity student;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lecture_id")
    private LectureTimeEntity lectureTime;


    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
