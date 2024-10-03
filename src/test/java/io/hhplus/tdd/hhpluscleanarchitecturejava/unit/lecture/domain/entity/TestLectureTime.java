package io.hhplus.tdd.hhpluscleanarchitecturejava.unit.lecture.domain.entity;

import io.hhplus.tdd.hhpluscleanarchitecturejava.common.domain.BusinessError;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.entity.Lecture;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.entity.LectureTime;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class TestLectureTime {

    @Test
    public void 신청_인원_미달_시_성공() throws BusinessError {
        // GIVEN
        LectureTime lectureTime = LectureTime.builder()
                .id(1)
                .time(LocalDate.of(2024, 10, 1))
                .lecture(Lecture.builder().build())
                .studentCnt(0)
                .build();


        // WHEN
        lectureTime.registerValidate();

        // THEN


    }

    @Test
    public void 존재하지_않은_강좌일_떄_에러() throws BusinessError {
        // GIVEN
        LectureTime lectureTime = LectureTime.builder()
                .id(1)
                .time(LocalDate.of(2024, 10, 1))
                .studentCnt(0)
                .build();


        // WHEN
        BusinessError businessError = assertThrows(BusinessError.class, () -> lectureTime.registerValidate());

        // THEN
        assertEquals(lectureTime.getNOT_FOUNT_LECTURE_ERROR_MESSAGE(), businessError.getMessage());


    }

    @Test
    public void 시청인원_마감일_때_에러() throws BusinessError {
        // GIVEN
        LectureTime lectureTime = LectureTime.builder()
                .id(1)
                .time(LocalDate.of(2024, 10, 1))
                .lecture(Lecture.builder().build())
                .build();

        lectureTime.setStudentCnt(lectureTime.getMAX_REGISTER_STUDENT());


        // WHEN
        BusinessError businessError = assertThrows(BusinessError.class, () -> lectureTime.registerValidate());

        // THEN
        assertEquals(lectureTime.getMAX_REGISTER_STUDENT_ERROR_MESSAGE(), businessError.getMessage());


    }
}
