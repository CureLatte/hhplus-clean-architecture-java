package io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.repository;

import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.entity.Lecture;

public interface LectureRepository {

    public Lecture findById(long id);

}
