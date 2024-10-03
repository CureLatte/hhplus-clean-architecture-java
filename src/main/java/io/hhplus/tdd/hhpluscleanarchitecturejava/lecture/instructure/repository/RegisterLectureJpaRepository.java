package io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.instructure.repository;

import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.entity.LectureTime;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.entity.RegisterLecture;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.repository.RegisterLectureRepository;
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
        String sqlquery = "select rl.* " +
                "from register_lecture as rl " +
                "inner join lecture_time as lt on rl.lecture_time_id = lt.id " +
                "where rl.student_id= :studentId and (rl.lecture_time_id= :lectureTimeId or lt.lecture_id= :lectureId)";

        Query query = this.entityManager.createNativeQuery("" +
                sqlquery, RegisterLectureEntity.class);

        query.setParameter("studentId", student.getId());
        query.setParameter("lectureTimeId", lectureTime.getId());
        query.setParameter("lectureId", lectureTime.lectureId);


        List<RegisterLectureEntity> registerLectureEntityList = query.getResultList();

        if (registerLectureEntityList.size() <= 0) {
            return null;
        }

        return new RegisterLecture(registerLectureEntityList.get(0));
    }

    @Override
    public RegisterLecture create(Student student, LectureTime lectureTime) {

        RegisterLectureEntity newRegisterLecture = new RegisterLectureEntity();

        newRegisterLecture.setLectureTime(lectureTime.toEntity());
        newRegisterLecture.setStudent(student.toEntity());
        newRegisterLecture.setCreatedAt(LocalDateTime.now());
        newRegisterLecture.setUpdatedAt(LocalDateTime.now());


        this.entityManager.persist(newRegisterLecture);

        return null;
    }

    @Override
    public List<RegisterLecture> findAllByStudent(Student student) {
        String sqlQuery = "select * from register_lecture where student_id= :studentId";

        Query query = this.entityManager.createNativeQuery(sqlQuery, RegisterLectureEntity.class);
        query.setParameter("studentId", student.getId());

        List<RegisterLectureEntity> registerLectureEntityList = query.getResultList();

        return registerLectureEntityList.stream().map(RegisterLecture::new).toList();
    }


}
