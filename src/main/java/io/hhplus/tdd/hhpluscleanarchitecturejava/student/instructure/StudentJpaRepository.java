package io.hhplus.tdd.hhpluscleanarchitecturejava.student.instructure;

import io.hhplus.tdd.hhpluscleanarchitecturejava.student.domain.Student;
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


    @Override
    public Student findById(long id) {
        StudentEntity entity = this.entityManager.find(StudentEntity.class, id);

        System.out.println("Entity CHECK: " + entity);

        if (entity == null) {
            return null;
        }
        return new Student(entity);
    }
}
