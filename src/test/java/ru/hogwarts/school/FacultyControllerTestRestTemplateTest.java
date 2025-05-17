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
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTestRestTemplateTest {

    @LocalServerPort
    private int port;
    @Autowired
    private FacultyController controller;
    @Autowired
    private TestRestTemplate template;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    void createOneFaculty() {
        Faculty testFaculty = new Faculty();
        testFaculty.setName("testName");
        testFaculty.setColor("38");

        Faculty expectedTestFaculty = template.postForObject(getBaseUrl() + "/create", testFaculty, Faculty.class);
        Assertions.assertThat(expectedTestFaculty).isNotNull();
        //with exchange
        HttpEntity<Faculty> request = new HttpEntity<>(testFaculty);
        ResponseEntity<Faculty> response = template.exchange(
                getBaseUrl() + "/create",
                HttpMethod.POST,
                request,
                Faculty.class
        );

        assertNotNull(expectedTestFaculty.getClass());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("testName", response.getBody().getName());
        assertEquals(expectedTestFaculty.getName(), response.getBody().getName());
    }

    @Test
    void getOneFaculty() throws Exception {
        Faculty testFaculty = new Faculty();

        testFaculty.setName("testName2");
        testFaculty.setColor("63");

        HttpEntity<Faculty> request = new HttpEntity<>(testFaculty);
        ResponseEntity<Faculty> response = template.exchange(
                getBaseUrl() + "/create",
                HttpMethod.POST,
                request,
                Faculty.class
        );
        Faculty reternCreatedFaculty = Objects.requireNonNull(response.getBody());

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
    void updateFaculty_shouldReturnUpdatedFaculty() {
        Faculty testFaculty = new Faculty();
        testFaculty.setName("testName3");
        testFaculty.setColor("85");


        HttpEntity<Faculty> request = new HttpEntity<>(testFaculty);
        ResponseEntity<Faculty> response = template.exchange(
                getBaseUrl() + "/create",
                HttpMethod.POST,
                request,
                Faculty.class
        );
        Faculty reternCreatedFaculty = Objects.requireNonNull(response.getBody());


        reternCreatedFaculty.setName("updatedTestFaculty");

        HttpEntity<Faculty> requestUpdatedFaculty = new HttpEntity<>(reternCreatedFaculty);
        ResponseEntity<Faculty> responseUpdatedFaculty = template.exchange(
                getBaseUrl() + "/update",
                HttpMethod.PUT,
                requestUpdatedFaculty,
                Faculty.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(responseUpdatedFaculty.getBody());
        assertEquals("updatedTestFaculty", responseUpdatedFaculty.getBody().getName());
        assertThat(responseUpdatedFaculty.getBody().getColor()).isEqualTo("85");
        assertEquals(reternCreatedFaculty.getId(), responseUpdatedFaculty.getBody().getId());
    }

    private String getBaseUrl() {
        return "http://localhost:" + port + "/faculty";
    }

    @Test
    void deleteOneFaculty() throws Exception {
        Faculty testFaculty = new Faculty();
        testFaculty.setName("testName5");
        testFaculty.setColor("122");

        Faculty expectedTestFaculty = template.postForObject(getBaseUrl() + "/create", testFaculty, Faculty.class);

        HttpEntity<Faculty> request = new HttpEntity<>(expectedTestFaculty);
        ResponseEntity<Faculty> response = template.exchange(
                getBaseUrl() + "/delete/" + expectedTestFaculty.getId(),
                HttpMethod.DELETE,
                request,
                Faculty.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());

        HttpEntity<Faculty> requestGet = new HttpEntity<>(expectedTestFaculty);
        ResponseEntity<Faculty> responseGet = template.exchange(
                getBaseUrl() + "/get/" + expectedTestFaculty.getId(),
                HttpMethod.GET,
                requestGet,
                Faculty.class
        );
        assertEquals(HttpStatus.NOT_FOUND, responseGet.getStatusCode());
    }
}