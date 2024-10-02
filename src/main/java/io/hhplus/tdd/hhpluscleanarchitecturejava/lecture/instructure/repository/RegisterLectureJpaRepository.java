package io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.instructure.repository;

import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.RegisterLectureRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class RegisterLectureJpaRepository implements RegisterLectureRepository {

    @PersistenceContext
    final private EntityManager entityManager;

    public RegisterLectureJpaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
