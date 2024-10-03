package io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.instructure.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Data
@Table(name = "lecture_time")
public class LectureTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "student_cnt", nullable = false)
    private long studentCnt;

    @Column(name = "time", nullable = false)
    private LocalDate time;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private LectureEntity lecture;

}
