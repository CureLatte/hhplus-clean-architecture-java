package io.hhplus.tdd.hhpluscleanarchitecturejava.unit.lecture.domain.service.LectureService;

import io.hhplus.tdd.hhpluscleanarchitecturejava.integration.TestBaseIntegration;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.instructure.entity.LectureEntity;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.instructure.entity.LectureTimeEntity;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.instructure.entity.RegisterLectureEntity;
import io.hhplus.tdd.hhpluscleanarchitecturejava.student.instructure.StudentEntity;
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
public class TestShowRegisterLectureList extends TestBaseIntegration {

    @Autowired
    EntityManager entityManager;

    public TestShowRegisterLectureList() {
        super();
    }

    private String getUrl(long id) {
        return "/lecture/register/" + id + "/history";
    }


    @Test
    public void 강의_신청_내역_조회_성공() throws Exception {
        // GIVEN
        StudentEntity student = new StudentEntity();
        student.setName("test1");

        this.entityManager.persist(student);

        LectureEntity lecture = new LectureEntity();
        lecture.setLecturer("test1");
        lecture.setTitle("test");

        this.entityManager.persist(lecture);

        LectureTimeEntity lectureTime = new LectureTimeEntity();
        lectureTime.setStudentCnt(0);
        lectureTime.setTime(LocalDate.now());
        lectureTime.setLecture(lecture);
        this.entityManager.persist(lectureTime);

        RegisterLectureEntity registerLecture = new RegisterLectureEntity();
        registerLecture.setStudent(student);
        registerLecture.setLectureTime(lectureTime);

        this.entityManager.persist(registerLecture);

        this.entityManager.flush();


        // WHEN
        ResultActions resultActions = mvc.perform(
                get(this.getUrl(student.getId()))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // THEN
        resultActions.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lectureHistory.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lectureHistory[0].lectureId").value(lecture.getId()))
        ;

    }
}
