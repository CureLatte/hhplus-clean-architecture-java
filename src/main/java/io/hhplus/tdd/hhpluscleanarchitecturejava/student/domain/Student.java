package io.hhplus.tdd.hhpluscleanarchitecturejava.student.domain;


import io.hhplus.tdd.hhpluscleanarchitecturejava.student.instructure.StudentEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Student {
    public long id;
    public String name;

    public Student(StudentEntity student) {
        this.id = student.getId();
        this.name = student.getName();
    }
}
