package io.hhplus.tdd.hhpluscleanarchitecturejava.unit.lecture.domain.service.LectureService;

import io.hhplus.tdd.hhpluscleanarchitecturejava.common.domain.BusinessError;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.LectureTime;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

public class TestGetLectureTimeById extends TestLectureService {
    public TestGetLectureTimeById() {
        super();
    }


    @Test
    public void 강의_시간_조회_성공() throws BusinessError {
        // GIVEN
        long id = 1;

        lenient()
                .when(this.lectureTimeRepository.findOneById(id))
                .thenReturn(LectureTime.builder().id(id).build());


        // WHEN
        LectureTime lectureTime = this.lectureService.getLectureTimeById(id);


        // THEN
        assertEquals(id, lectureTime.id);

    }

    @Test
    public void 강의_시간_조회_없을_때_에러() throws BusinessError {
        // GIVEN
        long id = 1;

        lenient()
                .when(this.lectureTimeRepository.findOneById(id))
                .thenReturn(null);


        // WHEN
        BusinessError businessError = assertThrows(BusinessError.class, () -> this.lectureService.getLectureTimeById(id));

        // THEN
        assertEquals(this.lectureService.NOT_FOUND_LECTURE_TIME_ERROR_MESSAGE, businessError.getMessage());

    }
}
