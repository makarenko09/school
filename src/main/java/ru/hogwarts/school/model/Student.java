package ru.hogwarts.school.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import ru.hogwarts.school.service.FieldValidationException;

import java.util.Objects;

@Entity
public class Student implements ValidateUnique {
        @Id
        @GeneratedValue
        private Long id;
        @ManyToOne
        @JoinColumn(name = "faculty_id")
        @JsonBackReference
        private Faculty faculty;

    private String name;
    private int age;

    public Student(String name, int age) {
        validControl(name, age);
            this.name = name;
            this.age = age;
        }

    public Student(String name) {
        validControl(name, 0);
        age = 20;
        this.name = name;
    }

    public Student(Long id, String name, int age) {
        validControl(name, age);
            this.id = id;
            this.name = name;
            this.age = age;
        }

    public Student() {

        }

    public void validControl(String name, int age) {
        String input = String.valueOf(age);
        //один или ряд нулей
        boolean isValidFor20Age = input.matches("^0+$");
        //один или ряд нулей с числом возраста
        boolean isInvalidForAge = input.matches("^0+[0-9]+$");
        if (isValidFor20Age) {
            age = 20;
        }

        if ((age < 16) || isInvalidForAge) {
            throw new FieldValidationException(input);
        }
        validName(name);
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public Student newObject(Long id, String name, int age) {
            return new Student(id, name, age);
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Student student)) return false;
            return age == student.age && Objects.equals(id, student.id) && Objects.equals(name, student.name);
        }

        @Override
        public String toString() {
            return "Student{" + "id=" + id + ", faculty=" + faculty + ", name='" + name + '\'' + ", age=" + age + '}';
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name, age);
        }


    @Override
    public String getContentType() {
        return this.getClass().getSimpleName();
    }
}
