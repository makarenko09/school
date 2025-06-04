package ru.hogwarts.school.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Objects;

    @Entity
    public class Student {
        @Id
        @GeneratedValue
        private Long id;
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "faculty_id")
        @JsonBackReference
        private Faculty faculty;

        public Faculty getFaculty() {
            return faculty;
        }

        public void setFaculty(Faculty faculty) {
            this.faculty = faculty;
        }

        public Student(String name, int age) {
            this.name = name;
            this.age = age;
        }

        private String name;
        private int age;

        public Student(Long id, String name, int age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }

        public Student(Long id, Faculty faculty, String name, int age) {
            this.id = id;
            this.faculty = faculty;
            this.name = name;
            this.age = age;
        }

        public Student(Faculty faculty) {
            this.faculty = faculty;
        }

        public Student() {

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
        public String toString() {
            return "Student{" + "id=" + id + ", faculty=" + faculty + ", name='" + name + '\'' + ", age=" + age + '}';
        }

        @Override
        public boolean equals(Object o) {
            if ((this == o)) return true;
            if (!(o == null || getClass() != o.getClass())) return false;
            Student student = (Student) o;

            return id!= null ? id.equals(student.id) : student.id == null;
        }
        @Override
        public int hashCode() {
            return id != null ? id.hashCode() : 0;
        }
    }
