package io.hhplus.tdd.hhpluscleanarchitecturejava.integration.lecture;

import io.hhplus.tdd.hhpluscleanarchitecturejava.common.domain.BusinessError;
import io.hhplus.tdd.hhpluscleanarchitecturejava.integration.TestBaseIntegration;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.repository.LectureRepository;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.repository.LectureTimeRepository;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.instructure.entity.LectureEntity;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.instructure.entity.LectureTimeEntity;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
public class TestGetLectureTimeList extends TestBaseIntegration {

    final String baseUrl = "/lecture";

    @Autowired
    LectureRepository lectureRepository;

    @Autowired
    LectureTimeRepository lectureTimeRepository;

    @Autowired
    EntityManager entityManager;

    public TestGetLectureTimeList() throws BusinessError {
        super();
    }

    @Test
    public void 날자별_강좌_조회_성공() throws Exception {
        // GIVEN
        LocalDate today = LocalDate.now();

        LectureEntity lecture = new LectureEntity();
        lecture.setLecturer("test1");
        lecture.setTitle("title");

        this.entityManager.persist(lecture);

        LectureTimeEntity lectureTime = new LectureTimeEntity();

        lectureTime.setLecture(lecture);
        lectureTime.setTime(today);

        this.entityManager.persist(lectureTime);


        // WHEN
        ResultActions resultActions = mvc.perform(
                get(this.baseUrl + "/time")
                        .queryParam("date", today.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        // THEN
        resultActions
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lectureTimeList.length()").value(1));
        ;
    }


    @Test
    @Transactional
    public void 날자별_강좌_조회_다른_일자_추가_성공() throws Exception {
        // GIVEN
        LocalDate today = LocalDate.now();

        LectureEntity lecture = new LectureEntity();
        lecture.setLecturer("test1");
        lecture.setTitle("title");

        this.entityManager.persist(lecture);

        LectureTimeEntity lectureTime = new LectureTimeEntity();

        lectureTime.setLecture(lecture);
        lectureTime.setTime(today);

        this.entityManager.persist(lectureTime);

        LectureTimeEntity lectureTime2 = new LectureTimeEntity();
        lectureTime2.setLecture(lecture);
        lectureTime2.setTime(today.plusDays(1));

        this.entityManager.persist(lectureTime2);


        // WHEN
        ResultActions resultActions = mvc.perform(
                get(this.baseUrl + "/time")
                        .queryParam("date", today.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        // THEN
        resultActions
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lectureTimeList.length()").value(1));
        ;
    }

    @Test
    @Transactional
    public void 날자별_강좌_조회_다른_일자_조회_실패() throws Exception {
        // GIVEN
        LocalDate today = LocalDate.now();

        LectureEntity lecture = new LectureEntity();
        lecture.setLecturer("test1");
        lecture.setTitle("title");

        this.entityManager.persist(lecture);

        LectureTimeEntity lectureTime = new LectureTimeEntity();

        lectureTime.setLecture(lecture);
        lectureTime.setTime(today);

        this.entityManager.persist(lectureTime);


        // WHEN
        ResultActions resultActions = mvc.perform(
                get(this.baseUrl + "/time")
                        .queryParam("date", today.plusDays(1).toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        // THEN
        resultActions
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lectureTimeList.length()").value(0));
        ;
    }


    @Test
    @Transactional
    public void 강좌_신청시_성공() throws Exception {
        // GIVEN
        LocalDate today = LocalDate.now();

        LectureEntity lecture = new LectureEntity();
        lecture.setLecturer("test1");
        lecture.setTitle("title");

        this.entityManager.persist(lecture);

        LectureTimeEntity lectureTime = new LectureTimeEntity();

        lectureTime.setLecture(lecture);
        lectureTime.setTime(today);

        this.entityManager.persist(lectureTime);


        // WHEN
        ResultActions resultActions = mvc.perform(
                get(this.baseUrl + "/time")
                        .queryParam("date", today.plusDays(1).toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        // THEN
        resultActions
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lectureTimeList.length()").value(0));
        ;
    }


}
