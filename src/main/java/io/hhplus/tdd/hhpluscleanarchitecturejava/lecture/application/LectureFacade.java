package io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.application;


import io.hhplus.tdd.hhpluscleanarchitecturejava.common.domain.BusinessError;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.LectureService;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.entity.LectureTime;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.entity.RegisterLecture;
import io.hhplus.tdd.hhpluscleanarchitecturejava.student.domain.Student;
import io.hhplus.tdd.hhpluscleanarchitecturejava.student.domain.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Service
public class LectureFacade {
    final LectureService lectureService;
    final StudentService studentService;

    public List<LectureTime> getLectureTimeByDate(LocalDate targetDate) {
        return this.lectureService.showlectureTimeList(targetDate);
    }


    public void postRegisterLecture(long studentId, long lectureTimeId) throws BusinessError {
        Student student = this.studentService.getStudentById(studentId);
        LectureTime lectureTime = this.lectureService.getLectureTimeById(lectureTimeId);
        this.lectureService.registerWithLock(student, lectureTime);
    }

    public List<RegisterLecture> getLectureHistory(long studentId) throws BusinessError {
        Student student = this.studentService.getStudentById(studentId);
        return this.lectureService.showLectureHistory(student);
    }
}
