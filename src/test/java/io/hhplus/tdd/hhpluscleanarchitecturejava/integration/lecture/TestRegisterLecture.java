package io.hhplus.tdd.hhpluscleanarchitecturejava.integration.lecture;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.hhplus.tdd.hhpluscleanarchitecturejava.integration.TestBaseIntegration;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.LectureService;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.entity.Lecture;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.entity.LectureTime;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.entity.RegisterLecture;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.instructure.entity.LectureEntity;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.instructure.entity.LectureTimeEntity;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.instructure.entity.RegisterLectureEntity;
import io.hhplus.tdd.hhpluscleanarchitecturejava.student.domain.Student;
import io.hhplus.tdd.hhpluscleanarchitecturejava.student.domain.StudentService;
import io.hhplus.tdd.hhpluscleanarchitecturejava.student.instructure.StudentEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
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


public class TestRegisterLecture extends TestBaseIntegration {

    @Autowired
    EntityManager entityManager;

    @Autowired
    LectureService lectureService;

    @Autowired
    StudentService studentService;

    protected final String URL = "/lecture/register";

    protected ResultActions requestRegister(StudentEntity student, LectureTimeEntity lectureTime) throws Exception {
        System.out.println("studentGETID!!!!: " + student.getId());

        Map<String, Long> request = new HashMap<String, Long>();
        request.put("studentId", student.getId());
        request.put("lectureTimeId", lectureTime.getId());

        return mvc.perform(
                post(this.URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))

        );

    }

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
        ResultActions resultActions = this.requestRegister(student, lectureTime);
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

        lectureTime.setId(lecture.getId() + 1);

        // WHEN
        ResultActions resultActions = this.requestRegister(student, lectureTime);


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

        student.setId(student.getId() + 1);

        // WHEN
        ResultActions resultActions = this.requestRegister(student, lectureTime);


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


        this.requestRegister(student, lectureTime);


        // WHEN
        ResultActions resultActions = this.requestRegister(student, lectureTime);


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

        this.requestRegister(student, lectureTime);

        // WHEN
        ResultActions resultActions = this.requestRegister(student, lectureTime2);


        // THEN
        resultActions
                .andExpect(status().is(500))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(lectureService.DUPLICATE_REGISTER_LECTURE_ERROR_MESSAGE));

    }

    @Test
    public void 인원_마감_강의_신청시_에러() throws Exception {
        // GIVEN
        StudentEntity student = new StudentEntity();
        student.setName("testStudent");

        this.entityManager.persist(student);

        LectureEntity lecture = new LectureEntity();

        lecture.setLecturer("testLecturer");
        lecture.setTitle("testTitle");

        this.entityManager.persist(lecture);

        LectureTime defaultLectureTime = LectureTime.builder().build();

        LectureTimeEntity lectureTime = new LectureTimeEntity();
        lectureTime.setTime(LocalDate.now());
        lectureTime.setLecture(lecture);
        lectureTime.setStudentCnt(defaultLectureTime.getMAX_REGISTER_STUDENT());

        this.entityManager.persist(lectureTime);


        // WHEN

        ResultActions resultActions = this.requestRegister(student, lectureTime);

        // THEN
        resultActions
                .andExpect(status().is(500))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(defaultLectureTime.getMAX_REGISTER_STUDENT_ERROR_MESSAGE()));

    }


}
