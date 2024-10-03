package io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.instructure.repository;

import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.entity.LectureTime;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.repository.LectureTimeRepository;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.instructure.entity.LectureTimeEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class LectureTimeJpaRepository implements LectureTimeRepository {


    @PersistenceContext
    final EntityManager entityManager;

    @Autowired
    public LectureTimeJpaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;

    }

    @Override
    public LectureTime findOneById(long id) {

        LectureTimeEntity lectureTimeEntity = entityManager.find(LectureTimeEntity.class, id);

        if (lectureTimeEntity == null) return null;

        return new LectureTime(lectureTimeEntity);
    }

    @Override
    public List<LectureTime> findAllByLectureId(long id) {
        return List.of();
    }

    @Override
    public List<LectureTime> findAllByTime(LocalDate date) {

        Query nativeQuery = entityManager.createNativeQuery("select * from lecture_time where time = :date", LectureTimeEntity.class);
        nativeQuery.setParameter("date", date);
        List<LectureTimeEntity> checkLectureTimeEntities = nativeQuery.getResultList();

        return checkLectureTimeEntities.stream().map(
                        e -> new LectureTime(e))
                .collect(Collectors.toList());
    }
}
