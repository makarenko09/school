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
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTestRestTemplateTest {
    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController controller;
    @Autowired
    private TestRestTemplate template;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private FacultyRepository facultyRepository;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

    private String getBaseUrl() {
        return "http://localhost:" + port + "/faculty";
    }

    private String getBaseUrlForStudentsTest() {
        return "http://localhost:" + port + "/student";
    }

    @Test
    void createOneFacultyTest() {
        Faculty testFaculty = new Faculty();
        testFaculty.setName("testName1");
        testFaculty.setColor("61");

        Faculty expectedTestFaculty = template.postForObject(getBaseUrl() + "/create", testFaculty, Faculty.class);
        Assertions.assertThat(expectedTestFaculty).isNotNull();
        assertNotNull(expectedTestFaculty.getClass());

        HttpEntity<Faculty> request = new HttpEntity<>(testFaculty);
        ResponseEntity<Faculty> response = template.exchange(
                getBaseUrl() + "/create",
                HttpMethod.POST,
                request,
                Faculty.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        Faculty faculty = response.getBody();
        assertEquals(faculty.getName(), expectedTestFaculty.getName());
        assertEquals("testName1", response.getBody().getName());
        assertEquals(expectedTestFaculty.getName(), response.getBody().getName());
    }

    @Test
    void createManyFacultiesTest() {
        Faculty testFaculty1 = new Faculty();
        testFaculty1.setName("testName2");
        testFaculty1.setColor("87");

        Faculty testFaculty2 = new Faculty();
        testFaculty2.setName("testName3");
        testFaculty2.setColor("91");

        List<Faculty> testFaculties = new ArrayList<>(Arrays.asList(testFaculty1, testFaculty2));

        HttpEntity<List<Faculty>> request = new HttpEntity<>(testFaculties);
        ResponseEntity<List<Faculty>> response = template.exchange(getBaseUrl() + "/create/many", HttpMethod.POST, request, new ParameterizedTypeReference<List<Faculty>>() {
        });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        List<Faculty> faculties = response.getBody();
        assertNotNull(faculties);
        assertNotNull(response.getClass());
        assertTrue(faculties.stream().anyMatch(s -> s.getName().equals(testFaculty1.getName())));
        assertTrue(faculties.stream().anyMatch(s -> s.getName().equals(testFaculty2.getName())));
    }

    @Test
    public void addStudentsToFacultyTest() {
        Student student1 = new Student();
        student1.setName("testName111");
        student1.setAge(112);
        Student student2 = new Student();
        student2.setName("testName114");
        student2.setAge(115);
        List<Student> students = List.of(student1, student2);

        studentRepository.saveAll(students);

        String nameFaculty = "testFacultyName4";
        Long facultyId = 55L;
        final String colorFaculty = "testFacultyColor1";
        Faculty facultyExtend = new Faculty();
        facultyExtend.setId(facultyId);
        facultyExtend.setName(nameFaculty);
        facultyExtend.setColor(colorFaculty);

        facultyRepository.save(facultyExtend);

        String url = "/faculty/add/students/" + facultyExtend.getId();
        ResponseEntity<Void> voidResponseEntity = template.postForEntity(url, List.of(student1, student2), Void.class);

        assertEquals(HttpStatus.OK, voidResponseEntity.getStatusCode());

        HttpEntity<Faculty> requestCreatedFaculty = new HttpEntity<>(facultyExtend);
        ResponseEntity<Faculty> responseSavedFaculty = template.exchange(getBaseUrl() + "/get/" + facultyExtend.getId(), HttpMethod.GET, requestCreatedFaculty, Faculty.class);

        assertEquals(HttpStatus.OK, responseSavedFaculty.getStatusCode());
        assertNotNull(responseSavedFaculty.getBody());
        assertEquals(facultyExtend.getName(), responseSavedFaculty.getBody().getName());
        assertEquals(facultyExtend.getId(), responseSavedFaculty.getBody().getId());
    }

    @Test
    void getOneFacultyTest() throws Exception {
        Faculty testFaculty = new Faculty();
        testFaculty.setName("testName5");
        testFaculty.setColor("148");

        HttpEntity<Faculty> request = new HttpEntity<>(testFaculty);
        ResponseEntity<Faculty> response = template.exchange(
                getBaseUrl() + "/create",
                HttpMethod.POST,
                request,
                Faculty.class
        );

        Faculty reternCreatedFaculty = Objects.requireNonNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        HttpEntity<Faculty> requestCreatedFaculty = new HttpEntity<>(reternCreatedFaculty);
        ResponseEntity<Faculty> responseCreatedFaculty = template.exchange(
                getBaseUrl() + "/get/" + reternCreatedFaculty.getId(),
                HttpMethod.GET,
                requestCreatedFaculty,
                Faculty.class
        );

        assertEquals(HttpStatus.OK, responseCreatedFaculty.getStatusCode());
        assertNotNull(responseCreatedFaculty.getBody());
        assertEquals(testFaculty.getName(), responseCreatedFaculty.getBody().getName());
        assertEquals(reternCreatedFaculty.getId(), response.getBody().getId());
    }

    @Test
    void getFacultiesByColorTest() {
        Faculty testFaculty1 = new Faculty();
        testFaculty1.setName("testName6");
        testFaculty1.setColor("179-183");

        Faculty testFaculty2 = new Faculty();
        testFaculty2.setName("testName7");
        testFaculty2.setColor("179-183");

        List<Faculty> testFaculties = new ArrayList<>(Arrays.asList(testFaculty1, testFaculty2));

        HttpEntity<List<Faculty>> request = new HttpEntity<>(testFaculties);
        ResponseEntity<List<Faculty>> response = template.exchange(
                getBaseUrl() + "/create/many",
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<List<Faculty>>() {
                }
        );

        ResponseEntity<List<Faculty>> responseGet = template.exchange(
                getBaseUrl() + "/get/many/179-183",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Faculty>>() {
                }
        );

        assertEquals(HttpStatus.OK, responseGet.getStatusCode());
        assertNotNull(responseGet.getBody());
        List<Faculty> faculties = responseGet.getBody();
        assertNotNull(faculties);
        assertTrue(faculties.stream().anyMatch(s -> s.getColor().equals("179-183")));
    }

    @Test
    void getStudentsOfFacultyTest() {
        Student student1 = new Student();
        student1.setName("testName214");
        student1.setAge(215);
        Student student2 = new Student();
        student2.setName("testName217");
        student2.setAge(218);

        studentRepository.saveAll(List.of(student1, student2));

        Faculty faculty = new Faculty();
        faculty.setName("testFacultyName8");
        faculty.setColor("testColor224");
        facultyRepository.save(faculty);

        String url = "/faculty/add/students/" + faculty.getId();
        ResponseEntity<Void> voidResponseEntity = template.postForEntity(url, List.of(student1, student2), Void.class);

        assertEquals(HttpStatus.OK, voidResponseEntity.getStatusCode());

        ResponseEntity<Student[]> response = template.getForEntity(
                "/faculty/get/students/" + faculty.getId(), Student[].class);

        assertNotNull(response.getBody());
        Student[] returnedStudents = response.getBody();
        assertNotNull(returnedStudents);
        assertEquals(2, returnedStudents.length);
    }

    @Test
    void updateFaculty_shouldReturnUpdatedFacultyTest() {
        Faculty testFaculty = new Faculty();
        testFaculty.setName("testName9");
        testFaculty.setColor("245");

        HttpEntity<Faculty> request = new HttpEntity<>(testFaculty);
        ResponseEntity<Faculty> response = template.exchange(getBaseUrl() + "/create", HttpMethod.POST, request, Faculty.class);

        Faculty reternCreatedFaculty = Objects.requireNonNull(response.getBody());
        reternCreatedFaculty.setName("updatedTestFaculty1");

        HttpEntity<Faculty> requestUpdatedFaculty = new HttpEntity<>(reternCreatedFaculty);
        ResponseEntity<Faculty> responseUpdatedFaculty = template.exchange(getBaseUrl() + "/update", HttpMethod.PUT, requestUpdatedFaculty, Faculty.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(responseUpdatedFaculty.getBody());
        assertEquals("updatedTestFaculty1", responseUpdatedFaculty.getBody().getName());
        assertThat(responseUpdatedFaculty.getBody().getColor()).isEqualTo("245");
        assertEquals(reternCreatedFaculty.getId(), responseUpdatedFaculty.getBody().getId());
    }

    @Test
    void deleteOneFacultyTest() throws Exception {
        Faculty testFaculty = new Faculty();
        testFaculty.setName("testName10");
        testFaculty.setColor("267");

        Faculty expectedTestFaculty = template.postForObject(getBaseUrl() + "/create", testFaculty, Faculty.class);

        HttpEntity<Faculty> request = new HttpEntity<>(expectedTestFaculty);
        ResponseEntity<Faculty> response = template.exchange(getBaseUrl() + "/delete/" + expectedTestFaculty.getId(), HttpMethod.DELETE, request, Faculty.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        HttpEntity<Faculty> requestGet = new HttpEntity<>(expectedTestFaculty);
        ResponseEntity<Faculty> responseGet = template.exchange(getBaseUrl() + "/get/" + expectedTestFaculty.getId(), HttpMethod.GET, requestGet, Faculty.class);

        assertEquals(HttpStatus.NOT_FOUND, responseGet.getStatusCode());
    }
}