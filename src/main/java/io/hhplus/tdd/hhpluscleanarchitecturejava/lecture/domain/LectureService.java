package io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain;


import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LectureService {
    final LectureRepository lectureRepository;
    final LectureTimeRepository lectureTimeRepository;

    public LectureService(LectureRepository lectureRepository, LectureTimeRepository lectureTimeRepository) {
        this.lectureRepository = lectureRepository;
        this.lectureTimeRepository = lectureTimeRepository;
    }


    public List<LectureTime> getLectureList(LocalDate targetDate) {
        return this.lectureTimeRepository.findAllByTime(targetDate);
    }


}
