package io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain;

import java.time.LocalDateTime;
import java.util.Date;

public class LectureTime {
    public long id;
    public int lectureId;
    public int studentCnt;
    public int time;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;

    public LectureTime(int lectureId, int studentCnt, int time, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.lectureId = lectureId;
        this.studentCnt = studentCnt;
        this.time = time;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
