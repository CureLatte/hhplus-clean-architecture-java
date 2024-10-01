package io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain;

import java.util.List;

public interface LectureTimeRepository {
    public List<LectureTime> findAllByLectureId(long id);

}
