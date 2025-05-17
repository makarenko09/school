package ru.hogwarts.school;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

        HttpEntity<Student> request = new HttpEntity<>(expectedTestStudent);
        ResponseEntity<Student> response = template.exchange(
                getBaseUrl() + "/get/" + expectedTestStudent.getId(),
                HttpMethod.GET,
                request,
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
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertEquals("updatedTestStudent", response.getBody().getName());
//        assertThat(response.getBody().getAge()).isEqualTo(84);
//        assertEquals(created.getId(), response.getBody().getId());
    }

    private String getBaseUrl() {
        return "http://localhost:" + port + "/student";
    }

    @Test
    void deleteOneStudent() throws Exception {
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

        HttpEntity<Student> requestGet = new HttpEntity<>(expectedTestStudent);
        ResponseEntity<Student> responseGet = template.exchange(
                getBaseUrl() + "/get/" + expectedTestStudent.getId(),
                HttpMethod.GET,
                requestGet,
                Student.class
        );
        assertEquals(HttpStatus.NOT_FOUND, responseGet.getStatusCode());
    }
}