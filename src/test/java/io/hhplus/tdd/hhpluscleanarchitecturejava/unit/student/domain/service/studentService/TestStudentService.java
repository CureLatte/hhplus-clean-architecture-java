package io.hhplus.tdd.hhpluscleanarchitecturejava.unit.student.domain.service.studentService;

import io.hhplus.tdd.hhpluscleanarchitecturejava.common.domain.BusinessError;
import io.hhplus.tdd.hhpluscleanarchitecturejava.student.domain.Student;
import io.hhplus.tdd.hhpluscleanarchitecturejava.student.domain.StudentRepository;
import io.hhplus.tdd.hhpluscleanarchitecturejava.student.domain.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class TestStudentService {
    @InjectMocks
    StudentService studentService;

    @Mock
    StudentRepository studentRepository;


    @Test
    public void 학생_정보_조회시_성공() throws BusinessError {
        // GIVEN
        long id = 1;
        lenient()
                .when(this.studentRepository.findById(id))
                .thenReturn(Student.builder().id(id).build());


        // WHEN
        Student student = this.studentService.getStudentById(id);

        // THEN
        assertEquals(id, student.getId());

    }

    @Test
    public void 학생_정보_조회_실패_시_에러() throws BusinessError {
        // GIVEN
        long id = 1;
        lenient()
                .when(this.studentRepository.findById(id))
                .thenReturn(null);


        // WHEN
        BusinessError businessError = assertThrows(BusinessError.class, () ->
                this.studentService.getStudentById(id));

        // THEN
        assertEquals(this.studentService.NOT_FOUND_STUDENT_ERROR_MESSAGE, businessError.getMessage());

    }
}
