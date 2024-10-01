package io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain;

import org.springframework.stereotype.Service;

@Service
public class LectureService {
    final LectureRepository lectureRepository;
    final LectureTimeRepository lectureTimeRepository;

    public LectureService(LectureRepository lectureRepository, LectureTimeRepository lectureTimeRepository) {
        this.lectureRepository = lectureRepository;
        this.lectureTimeRepository = lectureTimeRepository;
    }


}
