package io.hhplus.tdd.hhpluscleanarchitecturejava.integration.lecture;

import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.instructure.entity.LectureEntity;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.instructure.entity.LectureTimeEntity;
import io.hhplus.tdd.hhpluscleanarchitecturejava.student.domain.StudentRepository;
import io.hhplus.tdd.hhpluscleanarchitecturejava.student.instructure.StudentEntity;
import jakarta.persistence.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class TestRegisterLectureWithConcurrency extends TestRegisterLecture {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EntityManagerFactory emf;

    public TestRegisterLectureWithConcurrency() {
        super();
    }


    @Test
    @Transactional(propagation = Propagation.NEVER)
    public void 동시_신청시_하나는_에러() throws Exception {

        // GIVEN
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        final StudentEntity studentEntity = new StudentEntity();
        studentEntity.setName("test1");


        entityManager.persist(studentEntity);


        System.out.println("student ID: " + studentEntity.getId());

        LectureEntity lectureEntity = new LectureEntity();
        lectureEntity.setLecturer("test2");
        lectureEntity.setTitle("testTitle");

        entityManager.persist(lectureEntity);

        final LectureTimeEntity lectureTimeEntity = new LectureTimeEntity();
        lectureTimeEntity.setLecture(lectureEntity);
        lectureTimeEntity.setTime(LocalDate.now());
        lectureTimeEntity.setStudentCnt(0);

        entityManager.persist(lectureTimeEntity);

        entityManager.flush();

        transaction.commit();

        int threadCnt = 5;

        ExecutorService executorService = Executors.newFixedThreadPool(threadCnt);

        CountDownLatch countDownLatch = new CountDownLatch(threadCnt);

        for (int i = 0; i < threadCnt; i++) {
            executorService.execute(() -> {


                try {


                    ResultActions resultActions = this.requestRegister(studentEntity, lectureTimeEntity);
                    resultActions.andDo(print());

                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    countDownLatch.countDown();
                }

            });

            System.out.println(i + " 번째 check!!");

        }

        countDownLatch.await();

        Thread.sleep(1000);


        // WHEN


        // THEN
    }

}
