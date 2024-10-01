package io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.instructure;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "lecture_time")
public class LectureTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "lecture_id", nullable = false)
    private long lectureId;

    @Column(name = "student_cnt", nullable = false)
    private long studentCnt;

    @Column(name = "time", nullable = false)
    private long time;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
