package io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain;

import java.time.LocalDate;
import java.util.List;

public interface LectureTimeRepository {

    public LectureTime findOneById(long id);

    public List<LectureTime> findAllByLectureId(long id);

    public List<LectureTime> findAllByTime(LocalDate Date);
}
