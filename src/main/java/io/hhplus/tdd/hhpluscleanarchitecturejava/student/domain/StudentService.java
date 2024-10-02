package io.hhplus.tdd.hhpluscleanarchitecturejava.student.domain;

import io.hhplus.tdd.hhpluscleanarchitecturejava.common.domain.BusinessError;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StudentService {
    final StudentRepository studentRepository;

    public Student getStudentById(long id) throws BusinessError {
        Student student = this.studentRepository.findById(id);

        if (student == null) {
            throw new BusinessError("존재 하지 않은 유저입니다.");
        }

        return student;
    }
}
