package io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.repository;

import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.entity.LectureTime;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.entity.RegisterLecture;
import io.hhplus.tdd.hhpluscleanarchitecturejava.student.domain.Student;

import java.util.List;

public interface RegisterLectureRepository {

    public RegisterLecture check(Student student, LectureTime lectureTime);

    public RegisterLecture create(Student student, LectureTime lectureTime);

    public List<RegisterLecture> findAllByStudent(Student student);
}
