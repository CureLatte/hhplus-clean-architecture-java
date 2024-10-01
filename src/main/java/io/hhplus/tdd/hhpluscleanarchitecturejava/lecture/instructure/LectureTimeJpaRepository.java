package io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.instructure;

import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.LectureTime;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.LectureTimeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}
