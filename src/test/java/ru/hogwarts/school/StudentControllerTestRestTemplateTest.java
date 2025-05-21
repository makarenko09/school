package ru.hogwarts.school;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTestRestTemplateTest {

    @LocalServerPort
    private int port;
    @Autowired
    private StudentController controller;
    @Autowired
    private TestRestTemplate template;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    void createOneStudent() {
        Student testStudent = new Student();
        testStudent.setName("testName");
        testStudent.setAge(37);

        Student expectedTestStudent = template.postForObject(getBaseUrl() + "/create", testStudent, Student.class);
        Assertions.assertThat(expectedTestStudent).isNotNull();
        //with exchange
        HttpEntity<Student> request = new HttpEntity<>(testStudent);
        ResponseEntity<Student> response = template.exchange(
                getBaseUrl() + "/create",
                HttpMethod.POST,
                request,
                Student.class
        );

        assertNotNull(expectedTestStudent.getClass());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("testName", response.getBody().getName());
        assertEquals(expectedTestStudent.getName(), response.getBody().getName());
    }

    @Test
    void getOneStudent() throws Exception {
        Student testStudent = new Student();

        testStudent.setName("testName2");
        testStudent.setAge(65);

        Student expectedTestStudent = template.postForObject(getBaseUrl() + "/create", testStudent, Student.class);

        ResponseEntity<Student> response = template.exchange(
                getBaseUrl() + "/get/" + expectedTestStudent.getId(),
                HttpMethod.GET,
                null,
                Student.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(testStudent.getName(), response.getBody().getName());
        assertEquals(expectedTestStudent.getId(), response.getBody().getId());
    }

    @Test
    void updateStudent_shouldReturnUpdatedStudent() {
        Student testStudent = new Student();
        testStudent.setName("testName3");
        testStudent.setAge(84);

        Student created = template.postForObject(getBaseUrl() + "/create", testStudent, Student.class);

        created.setName("updatedTestStudent");

        HttpEntity<Student> request = new HttpEntity<>(created);
        ResponseEntity<Student> response = template.exchange(
                getBaseUrl() + "/update",
                HttpMethod.PUT,
                request,
                Student.class
        );
    }

    private String getBaseUrl() {
        return "http://localhost:" + port + "/student";
    }

    @Test
    void deleteOneStudent() {
        Student testStudent = new Student();
        testStudent.setName("testName5");
        testStudent.setAge(122);

        Student expectedTestStudent = template.postForObject(getBaseUrl() + "/create", testStudent, Student.class);

        HttpEntity<Student> request = new HttpEntity<>(expectedTestStudent);
        ResponseEntity<Student> response = template.exchange(
                getBaseUrl() + "/delete/" + expectedTestStudent.getId(),
                HttpMethod.DELETE,
                request,
                Student.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());

        ResponseEntity<Student> responseGet = template.exchange(
                getBaseUrl() + "/get/" + expectedTestStudent.getId(),
                HttpMethod.GET,
                null,
                Student.class
        );
        assertEquals(HttpStatus.NOT_FOUND, responseGet.getStatusCode());
    }

    @Test
    void createManyStudents() {
        Student testStudent1 = new Student();
        testStudent1.setName("testName6");
        testStudent1
                .setAge(1351);

        Student testStudent2 = new Student();
        testStudent2.setName("testName7");
        testStudent2
                .setAge(1351);

        List<Student> testStudent = new ArrayList<Student>();
        testStudent.add(testStudent1);
        testStudent.add(testStudent2);
        HttpEntity<List<Student>> request = new HttpEntity<>(testStudent);
        ResponseEntity<List<Student>> response = template.exchange(
                getBaseUrl() + "/create/many",
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<List<Student>>() {
                }
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        List<Student> students = response.getBody();
        assertNotNull(students);
        assertNotNull(response.getClass());
        assertTrue(students.stream().anyMatch(s -> s.getName().equals(testStudent1.getName())));
        assertTrue(students.stream().anyMatch(s -> s.getName().equals(testStudent2.getName())));

    }
    @Test
    void getStudentsWithAgeBetween() {
        Student testStudentMin = new Student();
        Student testStudentMax = new Student();

        testStudentMin.setName("testName6");
        testStudentMin.setAge(1331);

        testStudentMax.setName("testName7");
        testStudentMax.setAge(1339);
        Student expectedTestStudentMin = template.postForObject(getBaseUrl() + "/create", testStudentMin, Student.class);
        Student expectedTestStudentMax = template.postForObject(getBaseUrl() + "/create", testStudentMax, Student.class);


        ResponseEntity<List<Student>> responseGet = template.exchange(
                getBaseUrl() + "/get/many?min=" + expectedTestStudentMin.getAge() + "&max=" + expectedTestStudentMax.getAge(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>() {}
        );
        assertEquals(HttpStatus.OK, responseGet.getStatusCode());
        assertNotNull(responseGet.getBody());
        List<Student> students = responseGet.getBody();
        assertNotNull(students);
        assertTrue(students.stream().anyMatch(s -> s.getName().equals(testStudentMin.getName())));
        assertTrue(students.stream().anyMatch(s -> s.getName().equals(testStudentMin.getName())));
    }

    @Test
    void getStudentsWithAge() {
        Student testStudent1 = new Student();
        Student testStudent2 = new Student();

        testStudent1.setName("testName8");
        testStudent1.setAge(1331);

        testStudent2.setName("testName9");
        testStudent2.setAge(1331);
        Student expectedTestStudentMin = template.postForObject(getBaseUrl() + "/create", testStudent1, Student.class);
        Student expectedTestStudentMax = template.postForObject(getBaseUrl() + "/create", testStudent2, Student.class);

        ResponseEntity<List<Student>> responseGet = template.exchange(
                getBaseUrl() + "/get/many/1331",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>() {
                }
        );
        assertEquals(HttpStatus.OK, responseGet.getStatusCode());
        assertNotNull(responseGet.getBody());
        List<Student> students = responseGet.getBody();
        assertNotNull(students);
        assertTrue(students.stream().anyMatch(s -> String.valueOf(s.getAge()).equals("1331")));
    }
}