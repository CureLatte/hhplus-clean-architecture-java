package io.hhplus.tdd.hhpluscleanarchitecturejava.unit.lecture.domain.service.LectureService;

import io.hhplus.tdd.hhpluscleanarchitecturejava.common.domain.BusinessError;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.Lecture;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.LectureTime;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.RegisterLecture;
import io.hhplus.tdd.hhpluscleanarchitecturejava.student.domain.Student;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

public class TestRegister extends TestLectureService {

    public TestRegister() {
        super();
    }


    @Test
    public void 강의_시간_등록_성공() throws BusinessError {
        // GIVEN
        Student student = Student.builder()
                .id(1)
                .name("test_name")
                .build();

        LectureTime lectureTime = LectureTime.builder()
                .id(1)
                .lectureId(1)
                .studentCnt(0)
                .build();

        when(this.registerLectureRepository.check(student, lectureTime)).thenReturn(null);
        when(this.registerLectureRepository.create(student, lectureTime)).thenReturn(RegisterLecture.builder().build());

        // WHEN
        this.lectureService.register(student, lectureTime);

        // THEN
        assertEquals(true, true);
    }

    @Test
    public void 강의_시간_중복_등록_에러() throws BusinessError {
        // GIVEN
        Student student = Student.builder()
                .id(1)
                .name("test_name")
                .build();

        LectureTime lectureTime = LectureTime.builder()
                .id(1)
                .lectureId(1)
                .studentCnt(0)
                .build();

        lenient()
                .when(this.registerLectureRepository.check(student, lectureTime))
                .thenReturn(RegisterLecture.builder().build());
        lenient()
                .when(this.registerLectureRepository.create(student, lectureTime))
                .thenReturn(RegisterLecture.builder().build());

        // WHEN
        BusinessError businessError = assertThrows(BusinessError.class, () -> lectureService.register(student, lectureTime));

        // THEN
        assertEquals(this.lectureService.DUPLICATE_REGISTER_LECTURE_ERROR_MESSAGE, businessError.getMessage());
    }

    @Test
    public void 강의_시간_신청_마감_검증_호출_확인() throws BusinessError {
        // GIVEN
        Student student = Student.builder()
                .id(1)
                .name("test_name")
                .build();

        LectureTime lectureTime = mock(LectureTime.class);

        lenient()
                .when(this.registerLectureRepository.check(student, lectureTime))
                .thenReturn(null);
        lenient()
                .when(this.registerLectureRepository.create(student, lectureTime))
                .thenReturn(RegisterLecture.builder().build());

        // WHEN
        lectureService.register(student, lectureTime);

        // THEN
        verify(lectureTime, times(1)).registerValidate();

    }
}
