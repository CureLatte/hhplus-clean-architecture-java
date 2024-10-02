package io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain;

import java.util.Optional;

public interface LectureRepository {

    public Lecture findById(long id);

}
