package io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain;

import io.hhplus.tdd.hhpluscleanarchitecturejava.student.domain.Student;

public interface RegisterLectureRepository {

    public RegisterLecture check(Student student, LectureTime lectureTime);

    public RegisterLecture create(Student student, LectureTime lectureTime);
}
