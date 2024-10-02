package io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.instructure.repository;

import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.LectureTime;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.RegisterLecture;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.RegisterLectureRepository;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.instructure.entity.RegisterLectureEntity;
import io.hhplus.tdd.hhpluscleanarchitecturejava.student.domain.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class RegisterLectureJpaRepository implements RegisterLectureRepository {

    @PersistenceContext
    final private EntityManager entityManager;

    public RegisterLectureJpaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * 중복 신청 조회
     *
     * @param student     Student
     * @param lectureTime LectureTime
     * @return RegisterLecture
     */
    @Override
    public RegisterLecture check(Student student, LectureTime lectureTime) {
        Query query = this.entityManager.createNativeQuery("select * from register_lecture where student_id= :studentId and lecture_time_id= :lectureTimeId", RegisterLectureEntity.class);

        query.setParameter("studentId", student.getId());
        query.setParameter("lectureTimeId", lectureTime.getId());


        List<RegisterLectureEntity> registerLectureEntityList = query.getResultList();

        System.out.println("Query RESEULT :: " + registerLectureEntityList);

        if (registerLectureEntityList.size() <= 0) {
            return null;
        }

        return new RegisterLecture(registerLectureEntityList.get(0));
    }

    @Override
    public RegisterLecture create(Student student, LectureTime lectureTime) {

        System.out.println("create Function" + student);

        RegisterLectureEntity newRegisterLecture = new RegisterLectureEntity();

        newRegisterLecture.setLectureTime(lectureTime.toEntity());
        newRegisterLecture.setStudent(student.toEntity());
        newRegisterLecture.setCreatedAt(LocalDateTime.now());
        newRegisterLecture.setUpdatedAt(LocalDateTime.now());


        this.entityManager.persist(newRegisterLecture);

        return null;
    }
}
