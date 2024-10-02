package io.hhplus.tdd.hhpluscleanarchitecturejava.student.instructure;

import io.hhplus.tdd.hhpluscleanarchitecturejava.student.domain.StudentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class StudentJpaRepository implements StudentRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    public StudentJpaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


}
