package io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.application;


import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.LectureService;
import org.springframework.stereotype.Component;

@Component
public class LectureFacade {
    final LectureService lectureService;

    public LectureFacade(LectureService lectureService) {
        this.lectureService = lectureService;
    }
}
