package io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.instructure.entity;


import io.hhplus.tdd.hhpluscleanarchitecturejava.student.instructure.StudentEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "register_lecture")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterLectureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private StudentEntity student;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lecture_time_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private LectureTimeEntity lectureTime;


    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
