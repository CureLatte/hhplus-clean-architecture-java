package io.hhplus.tdd.hhpluscleanarchitecturejava.student.domain;

import io.hhplus.tdd.hhpluscleanarchitecturejava.common.domain.BusinessError;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class StudentService {
    final StudentRepository studentRepository;

    public final String NOT_FOUND_STUDENT_ERROR_MESSAGE = "존재 하지 않은 유저입니다.";

    public Student getStudentById(long id) throws BusinessError {
        Student student = this.studentRepository.findById(id);

        if (student == null) {
            throw new BusinessError(this.NOT_FOUND_STUDENT_ERROR_MESSAGE);
        }

        return student;
    }
}
