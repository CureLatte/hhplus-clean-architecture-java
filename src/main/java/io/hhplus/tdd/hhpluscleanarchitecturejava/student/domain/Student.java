package io.hhplus.tdd.hhpluscleanarchitecturejava.student.domain;


import io.hhplus.tdd.hhpluscleanarchitecturejava.student.instructure.StudentEntity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class Student {
    public long id;
    public String name;

    public Student(StudentEntity student) {
        this.id = student.getId();
        this.name = student.getName();
    }

    public StudentEntity toEntity() {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setId(id);
        studentEntity.setName(name);
        return studentEntity;
    }
}
