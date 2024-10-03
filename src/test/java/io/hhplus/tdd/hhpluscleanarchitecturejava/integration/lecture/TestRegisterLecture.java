package io.hhplus.tdd.hhpluscleanarchitecturejava.integration.lecture;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.hhplus.tdd.hhpluscleanarchitecturejava.integration.TestBaseIntegration;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.LectureService;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.entity.Lecture;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.entity.RegisterLecture;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.instructure.entity.LectureEntity;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.instructure.entity.LectureTimeEntity;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.instructure.entity.RegisterLectureEntity;
import io.hhplus.tdd.hhpluscleanarchitecturejava.student.domain.Student;
import io.hhplus.tdd.hhpluscleanarchitecturejava.student.domain.StudentService;
import io.hhplus.tdd.hhpluscleanarchitecturejava.student.instructure.StudentEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
public class TestRegisterLecture extends TestBaseIntegration {

    @Autowired
    EntityManager entityManager;

    @Autowired
    LectureService lectureService;

    @Autowired
    StudentService studentService;

    private final String URL = "/lecture/register";

    public TestRegisterLecture() {
        super();
    }

    @Test
    public void 강좌_신청시_성공() throws Exception {
        // GIVEN
        StudentEntity student = new StudentEntity();

        student.setName("testStudent");

        this.entityManager.persist(student);

        LectureEntity lecture = new LectureEntity();

        lecture.setLecturer("testLecturer");
        lecture.setTitle("testTitle");

        this.entityManager.persist(lecture);

        LectureTimeEntity lectureTime = new LectureTimeEntity();
        lectureTime.setTime(LocalDate.now());
        lectureTime.setLecture(lecture);
        lectureTime.setStudentCnt(0);

        this.entityManager.persist(lectureTime);


        Map<String, Long> request = new HashMap<String, Long>();
        request.put("lectureTimeId", lectureTime.getId());
        request.put("studentId", student.getId());

        // WHEN
        ResultActions resultActions = mvc.perform(
                post(this.URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(request))

        );

        // THEN
        resultActions
                .andExpect(status().isOk());

        Query query = this.entityManager.createNativeQuery("select * from register_lecture where lecture_time_id = :lectureTimeId", RegisterLectureEntity.class);
        query.setParameter("lectureTimeId", lectureTime.getId());

        List<RegisterLectureEntity> lectureEntityList = query.getResultList();

        assertEquals(lectureEntityList.size(), 1);

        assertEquals(lectureEntityList.get(0).getLectureTime().getId(), lectureTime.getId());


    }

    @Test
    public void 존재하지_않은_강의_신청() throws Exception {
        // GIVEN
        StudentEntity student = new StudentEntity();
        student.setName("testStudent");

        this.entityManager.persist(student);

        LectureEntity lecture = new LectureEntity();

        lecture.setLecturer("testLecturer");
        lecture.setTitle("testTitle");

        this.entityManager.persist(lecture);

        LectureTimeEntity lectureTime = new LectureTimeEntity();
        lectureTime.setTime(LocalDate.now());
        lectureTime.setLecture(lecture);
        lectureTime.setStudentCnt(0);

        this.entityManager.persist(lectureTime);


        Map<String, Long> request = new HashMap<String, Long>();
        request.put("lectureTimeId", lectureTime.getId() + 1);
        request.put("studentId", student.getId());

        // WHEN
        ResultActions resultActions = mvc.perform(
                post(URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(request))
        );


        // THEN
        resultActions
                .andExpect(status().is(500))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(lectureService.NOT_FOUND_LECTURE_TIME_ERROR_MESSAGE));


    }

    @Test
    public void 존재하지_않은_학생일_경우_신청시_에러() throws Exception {
        // GIVEN
        StudentEntity student = new StudentEntity();
        student.setName("testStudent");

        this.entityManager.persist(student);

        LectureEntity lecture = new LectureEntity();

        lecture.setLecturer("testLecturer");
        lecture.setTitle("testTitle");

        this.entityManager.persist(lecture);

        LectureTimeEntity lectureTime = new LectureTimeEntity();
        lectureTime.setTime(LocalDate.now());
        lectureTime.setLecture(lecture);
        lectureTime.setStudentCnt(0);

        this.entityManager.persist(lectureTime);


        Map<String, Long> request = new HashMap<String, Long>();
        request.put("lectureTimeId", lectureTime.getId());
        request.put("studentId", student.getId() + 1);

        // WHEN
        ResultActions resultActions = mvc.perform(
                post(URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(request))
        );


        // THEN
        resultActions
                .andExpect(status().is(500))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(studentService.NOT_FOUND_STUDENT_ERROR_MESSAGE));

    }

    @Test
    public void 중복_강의_시간_신청시_에러() throws Exception {
        // GIVEN
        StudentEntity student = new StudentEntity();
        student.setName("testStudent");

        this.entityManager.persist(student);

        LectureEntity lecture = new LectureEntity();

        lecture.setLecturer("testLecturer");
        lecture.setTitle("testTitle");

        this.entityManager.persist(lecture);

        LectureTimeEntity lectureTime = new LectureTimeEntity();
        lectureTime.setTime(LocalDate.now());
        lectureTime.setLecture(lecture);
        lectureTime.setStudentCnt(0);

        this.entityManager.persist(lectureTime);


        Map<String, Long> request = new HashMap<String, Long>();
        request.put("lectureTimeId", lectureTime.getId());
        request.put("studentId", student.getId());
        mvc.perform(
                post(URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(request))
        );


        // WHEN
        ResultActions resultActions = mvc.perform(
                post(URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(request))
        );


        // THEN
        resultActions
                .andExpect(status().is(500))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(lectureService.DUPLICATE_REGISTER_LECTURE_ERROR_MESSAGE));

    }


    @Test
    public void 중복_강의_신청시_에러() throws Exception {
        // GIVEN
        StudentEntity student = new StudentEntity();
        student.setName("testStudent");

        this.entityManager.persist(student);

        LectureEntity lecture = new LectureEntity();

        lecture.setLecturer("testLecturer");
        lecture.setTitle("testTitle");

        this.entityManager.persist(lecture);

        LectureTimeEntity lectureTime = new LectureTimeEntity();
        lectureTime.setTime(LocalDate.now());
        lectureTime.setLecture(lecture);
        lectureTime.setStudentCnt(0);

        this.entityManager.persist(lectureTime);

        LectureTimeEntity lectureTime2 = new LectureTimeEntity();
        lectureTime2.setStudentCnt(0);
        lectureTime2.setTime(LocalDate.now().plusDays(1));
        lectureTime2.setLecture(lecture);

        this.entityManager.persist(lectureTime2);

        Map<String, Long> request = new HashMap<String, Long>();
        request.put("lectureTimeId", lectureTime.getId());
        request.put("studentId", student.getId());
        mvc.perform(
                post(URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(request))
        );


        request.clear();
        request.put("lectureTimeId", lectureTime2.getId());
        request.put("studentId", student.getId());
        // WHEN
        ResultActions resultActions = mvc.perform(
                post(URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(request))
        );


        // THEN
        resultActions
                .andExpect(status().is(500))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(lectureService.DUPLICATE_REGISTER_LECTURE_ERROR_MESSAGE));

    }

}
