package io.hhplus.tdd.hhpluscleanarchitecturejava.integration.lecture;

import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.entity.LectureTime;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.instructure.entity.LectureEntity;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.instructure.entity.LectureTimeEntity;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.instructure.entity.RegisterLectureEntity;
import io.hhplus.tdd.hhpluscleanarchitecturejava.student.domain.Student;
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

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;
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
    public void 신청_인원_이상_신청시_에러() throws InterruptedException {
        // GIVEN
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        LectureEntity lectureEntity = new LectureEntity();
        lectureEntity.setLecturer("test2");
        lectureEntity.setTitle("testTitle");

        entityManager.persist(lectureEntity);

        final LectureTimeEntity lectureTimeEntity = new LectureTimeEntity();
        lectureTimeEntity.setLecture(lectureEntity);
        lectureTimeEntity.setTime(LocalDate.now());
        lectureTimeEntity.setStudentCnt(0);

        entityManager.persist(lectureTimeEntity);

        transaction.commit();

        LectureTime lectureTime = LectureTime.builder().build();

        int threadCnt = (int) lectureTime.getMAX_REGISTER_STUDENT() + 10;

        ExecutorService executorService = Executors.newFixedThreadPool(threadCnt);

        CountDownLatch countDownLatch = new CountDownLatch(threadCnt);


        // WHEN
        for (int i = 0; i < threadCnt; i++) {
            executorService.execute(() -> {


                try {
                    EntityManager entityManagerAsync = emf.createEntityManager();
                    EntityTransaction transactionAsync = entityManagerAsync.getTransaction();
                    transactionAsync.begin();

                    StudentEntity studentEntity = new StudentEntity();
                    studentEntity.setName("test1");
                    entityManagerAsync.persist(studentEntity);
                    transactionAsync.commit();

                    ResultActions resultActions = this.requestRegister(studentEntity, lectureTimeEntity);
                    String resposeBodyString = resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

                    System.out.println("Response Result: " + resposeBodyString);

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


        // THEN
        String sqlQuery = "select * from register_lecture where lecture_time_id = :lectureTimeId";
        Query query = this.entityManager.createNativeQuery(sqlQuery, RegisterLectureEntity.class);
        query.setParameter("lectureTimeId", lectureTimeEntity.getId());

        List<RegisterLectureEntity> registerLectureEntityList = query.getResultList();

        assertEquals(lectureTime.getMAX_REGISTER_STUDENT(), registerLectureEntityList.size());
    }
}
