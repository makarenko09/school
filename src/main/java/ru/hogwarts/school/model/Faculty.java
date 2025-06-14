package ru.hogwarts.school.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@JsonIgnoreProperties(value = {"students"})
public class Faculty {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String color;

    @OneToMany(mappedBy = "faculty", orphanRemoval = true)
    @JsonManagedReference
    private List<Student> students = new ArrayList<>();

    public List<Student> getStudents() {
        return students;
    }

    public Faculty(Long id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public Faculty(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public Faculty() {

    }
    public void addStudent(Student student) {
        students.add(student);
        student.setFaculty(this);
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if ((this == o)) return true;
        if (!(o == null || getClass() != o.getClass())) return false;
        Faculty faculty = (Faculty) o;

        return id!= null ? id.equals(faculty.id) : faculty.id == null;
    }
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public Faculty newObject(Long id, String name, String color) {
        return new Faculty(id, name, color);
    }
}
