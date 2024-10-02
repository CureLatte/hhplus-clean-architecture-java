package io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.application;


import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.LectureService;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.LectureTime;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LectureFacade {
    final LectureService lectureService;

    public LectureFacade(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    public List<LectureTime> getLectureTimeByDate(LocalDate targetDate) {

        return this.lectureService.getLectureList(targetDate);
    }

}
