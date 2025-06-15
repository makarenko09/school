package ru.hogwarts.school.test.rest.template;


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
import ru.hogwarts.school.model.Faculty;
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

    private String getBaseUrl() {
        return "http://localhost:" + port + "/student";
    }

    @Test
    void createOneStudentTest() {
        Student testStudent = new Student();
        testStudent.setName("testName1");
        testStudent.setAge(48);

        Student expectedTestStudent = template.postForObject(getBaseUrl() + "/create", testStudent, Student.class);

        Assertions.assertThat(expectedTestStudent).isNotNull();
        assertNotNull(expectedTestStudent.getClass());

        HttpEntity<Student> request = new HttpEntity<>(testStudent);
        ResponseEntity<Student> response = template.exchange(
                getBaseUrl() + "/create",
                HttpMethod.POST,
                request,
                Student.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("testName1", response.getBody().getName());
        assertEquals(expectedTestStudent.getName(), response.getBody().getName());
    }

    @Test
    void getOneStudentTest() throws Exception {
        Student testStudent = new Student();
        testStudent.setName("testName2");
        testStudent.setAge(73);

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
    void getStudentsWithAgeTest() {
        Student testStudent1 = new Student();
        Student testStudent2 = new Student();
        testStudent1.setName("testName3");
        testStudent1.setAge(9699);
        testStudent2.setName("testName4");
        testStudent2.setAge(9699);

        Student expectedTestStudentMin = template.postForObject(getBaseUrl() + "/create", testStudent1, Student.class);
        Student expectedTestStudentMax = template.postForObject(getBaseUrl() + "/create", testStudent2, Student.class);

        ResponseEntity<List<Student>> responseGet = template.exchange(
                getBaseUrl() + "/get/many/9699",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>() {
                }
        );

        assertEquals(HttpStatus.OK, responseGet.getStatusCode());
        assertNotNull(responseGet.getBody());
        List<Student> students = responseGet.getBody();
        assertNotNull(students);
        assertTrue(students.stream().anyMatch(s -> String.valueOf(s.getAge()).equals("9699")));
    }

    @Test
    void getStudentsByRangeAgeTest() {
        Student testStudentMin = new Student();
        Student testStudentMax = new Student();
        testStudentMin.setName("testName5");
        testStudentMin.setAge(122);
        testStudentMax.setName("testName6");
        testStudentMax.setAge(124);

        Student expectedTestStudentMin = template.postForObject(getBaseUrl() + "/create", testStudentMin, Student.class);
        Student expectedTestStudentMax = template.postForObject(getBaseUrl() + "/create", testStudentMax, Student.class);

        ResponseEntity<List<Student>> responseGet = template.exchange(
                getBaseUrl() + "/get/many?min=" + expectedTestStudentMin.getAge() + "&max=" + expectedTestStudentMax.getAge(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>() {
                }
        );

        assertEquals(HttpStatus.OK, responseGet.getStatusCode());
        assertNotNull(responseGet.getBody());
        List<Student> students = responseGet.getBody();
        assertNotNull(students);
        assertTrue(students.stream().anyMatch(s -> s.getName().equals(testStudentMin.getName())));
        assertTrue(students.stream().anyMatch(s -> s.getName().equals(testStudentMin.getName())));
    }

    @Test
    public void getFacultyByStudentTest() throws Exception {
        Faculty testFaculty = new Faculty();
        testFaculty.setName("testName1");
        testFaculty.setColor("testColor1");

        Faculty faculty = template.postForObject("http://localhost:" + port + "/faculty" + "/create", testFaculty, Faculty.class);
        assertThat(faculty).isNotNull();
        Student testStudent1 = new Student();
        testStudent1.setName("testName7");
        testStudent1.setAge(1351);

        Student student = template.postForObject(getBaseUrl() + "/create", testStudent1, Student.class);

        String url = "/faculty/add/students/" + faculty.getId();
        ResponseEntity<Void> voidResponseEntity = template.postForEntity(url, List.of(student), Void.class);

        assertEquals(HttpStatus.OK, voidResponseEntity.getStatusCode());

        String url2 = "http://localhost:" + port + "/student/get/faculty/" + student.getId();
        ResponseEntity<Faculty> response = template.getForEntity(url2, Faculty.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(faculty.getId());
    }


    @Test
    void createManyStudentsTest() {
        Student testStudent1 = new Student();
        testStudent1.setName("testName8");
        testStudent1.setAge(177);

        Student testStudent2 = new Student();
        testStudent2.setName("testName9");
        testStudent2.setAge(181);

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
    void updateStudent_shouldReturnUpdatedStudentTest() {
        Student testStudent = new Student();
        testStudent.setName("testName10");
        testStudent.setAge(209);

        Student created = template.postForObject(getBaseUrl() + "/create", testStudent, Student.class);

        created.setName("updatedTestStudent1");

        HttpEntity<Student> request = new HttpEntity<>(created);
        ResponseEntity<Student> response = template.exchange(
                getBaseUrl() + "/update",
                HttpMethod.PUT,
                request,
                Student.class
        );
    }

    @Test
    void deleteOneStudentTest() {
        Student testStudent = new Student();
        testStudent.setName("testName11");
        testStudent.setAge(228);

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
}