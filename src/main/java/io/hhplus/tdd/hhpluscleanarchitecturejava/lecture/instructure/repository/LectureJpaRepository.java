package io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.instructure.repository;

import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.Lecture;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.LectureRepository;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.instructure.entity.LectureEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class LectureJpaRepository implements LectureRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public LectureJpaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Lecture> findById(long id) {
        LectureEntity lectureEntity = this.entityManager.find(LectureEntity.class, id);

        if (lectureEntity == null) {
            return Optional.empty();
        }

        return Optional.of(
                Lecture.builder()
                        .id(lectureEntity.getId())
                        .title(lectureEntity.getTitle())
                        .lecturer(lectureEntity.getLecturer())
                        .build());


    }


}
