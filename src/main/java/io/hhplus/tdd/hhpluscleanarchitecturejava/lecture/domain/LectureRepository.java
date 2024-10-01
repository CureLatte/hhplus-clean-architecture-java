package io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain;

import java.util.Optional;

public interface LectureRepository {

    public Optional<Lecture> findById(long id);

}
