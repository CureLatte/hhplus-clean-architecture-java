package io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.instructure;

import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.Lecture;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.LectureTime;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.LectureTimeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

@Repository
public class LectureTimeJpaRepository implements LectureTimeRepository {


    @PersistenceContext
    final EntityManager entityManager;

    @Autowired
    public LectureTimeJpaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;

    }

    @Override
    public List<LectureTime> findAllByLectureId(long id) {
        return List.of();
    }

    @Override
    public List<LectureTime> findAllByTime(LocalDate date) {
        TypedQuery<LectureTimeEntity> query = entityManager.createQuery("select t from LectureTimeEntity t join t.lecture where t.time = :date", LectureTimeEntity.class);
        query.setParameter("date", date);

        Query nativeQuery = entityManager.createNativeQuery("select * from lecture_time where time = :date", LectureTimeEntity.class);
        nativeQuery.setParameter("date", date);
        List<LectureTimeEntity> checkLectureTimeEntities = nativeQuery.getResultList();

        for (LectureTimeEntity lectureTimeEntity : checkLectureTimeEntities) {
            System.out.println("native Query Result: " + lectureTimeEntity);
        }

        List<LectureTimeEntity> lectureTimeEntityList = query.getResultList();

        return lectureTimeEntityList.stream().map(
                        e -> new LectureTime(e))
                .collect(Collectors.toList());
    }
}
