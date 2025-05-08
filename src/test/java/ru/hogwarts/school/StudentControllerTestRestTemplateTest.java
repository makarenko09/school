package ru.hogwarts.school;


import net.minidev.json.JSONObject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

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
        template = new TestRestTemplate();
        Student testStudent = new Student();
        testStudent.setName("testName");
        testStudent.setAge(43);

        Student expectedTestStudent = template.postForObject("http://localhost:" + port + "/student/create", testStudent, Student.class);
        Assertions.assertThat(expectedTestStudent).isNotNull();
    }

    @Test
    void createOneStudentWithJSON() throws Exception {
        template = new TestRestTemplate();

        JSONObject expectedUpdateStudents = new JSONObject();
        expectedUpdateStudents.put("name", "testName2");
        expectedUpdateStudents.put("age", "49");
        expectedUpdateStudents.put("age", "43");

//        Gson gson = new Gson();
//        String converToJson = new Gson().toJson(expectedUpdateStudents);
//        JSONObject expectedUpdateStudent = new JSONObject(converToJson);

        Student expectedTestPostStudent = template.postForObject("http://localhost:" + port + "/student/create", expectedUpdateStudents, Student.class);
        System.out.println(expectedTestPostStudent = template.postForObject("http://localhost:" + port + "/student/create", expectedUpdateStudents, Student.class));
//        Student expectedTestStudent = template.postForObject("http://localhost:" + port + "/student/create/many", List.of(), Student.class);

        Student responseTestGetStudent = template.getForObject("http://localhost:" + port + "/student/get" + expectedTestPostStudent.getId(), Student.class);
        System.out.println(responseTestGetStudent = template.getForObject("http://localhost:" + port + "/student/get" + expectedTestPostStudent.getId(), Student.class));
        Assertions.assertThat(responseTestGetStudent).isNotNull();
    }


    @Test
    void getOneStudent() throws Exception {
        template = new TestRestTemplate();
        Student testStudent = new Student();

        testStudent.setName("testName3.0");
        testStudent.setAge(39);

        JSONObject testStudentwithJSON = new JSONObject();
        testStudentwithJSON.put("name", "testName3.1");
        testStudentwithJSON.put("age", "99");
        testStudentwithJSON.put("age", "039");


        Student expectedTestStudent = template.postForObject("http://localhost:" + port + "/student/create", testStudent, Student.class);
        System.out.println(expectedTestStudent = template.postForObject("http://localhost:" + port + "/student/create", testStudentwithJSON, Student.class));

        Student expectedTestStudent2 = template.postForObject("http://localhost:" + port + "/student/create", testStudentwithJSON, Student.class);
        testStudentwithJSON.put("name", "testName3.2");
        System.out.println(expectedTestStudent2 = template.postForObject("http://localhost:" + port + "/student/create", testStudentwithJSON, Student.class));


        Student responseTestGetStudent = template.getForObject("http://localhost:" + port + "/student/get" + expectedTestStudent.getId(), Student.class);
        Assertions.assertThat(responseTestGetStudent).isNotNull();

        Student responseTestGetStudent2 = template.getForObject("http://localhost:" + port + "/student/get" + expectedTestStudent2.getId(), Student.class);


        Assertions.assertThat(responseTestGetStudent).isNotNull();
        Assertions.assertThat(responseTestGetStudent2).isNotNull();
        Assertions.assertThat(expectedTestStudent.getAge()).isEqualTo(expectedTestStudent.getAge());
        boolean result = Objects.equals(responseTestGetStudent2, responseTestGetStudent);
        if (result) {
            System.out.println("Objects.equals(responseTestGetStudent2, responseTestGetStudent)");
        }
//        result = Objects.equals(expectedTestStudent, responseTestGetStudent);
//        if (result) {
//            throw new IllegalArgumentException("Objects.equals(expectedTestStudent, responseTestGetStudent);");
//        }
//        result = Objects.equals(expectedTestStudent2, responseTestGetStudent2);
//        if (result) {
//            throw new IllegalArgumentException("Objects.equals(expectedTestStudent2, responseTestGetStudent2);");
//        }
//        result = Objects.equals(expectedTestStudent, expectedTestStudent2);
//        if (result) {
//            throw new IllegalArgumentException("Objects.equals(expectedTestStudent, expectedTestStudent2);");
//        }
    }

    @Test
    void updateOneStudent() throws Exception {
        template = new TestRestTemplate();
        Student testStudent = new Student();

        testStudent.setName("testName4.0");
        testStudent.setAge(54);

        JSONObject testStudentwithJSON = new JSONObject();
        testStudentwithJSON.put("name", "testName4.1");
        testStudentwithJSON.put("age", "0989");
        testStudentwithJSON.put("age", "54");


        Student expectedTestStudent = template.postForObject("http://localhost:" + port + "/student/create", testStudent, Student.class);
        System.out.println(expectedTestStudent = template.postForObject("http://localhost:" + port + "/student/create", testStudent, Student.class));


        Student expectedTestStudent2 = template.postForObject("http://localhost:" + port + "/student/create", testStudentwithJSON, Student.class);
        System.out.println(expectedTestStudent2 = template.postForObject("http://localhost:" + port + "/student/create", testStudentwithJSON, Student.class));


        expectedTestStudent.setAge(540);
        expectedTestStudent.setName("testName4.0-Student-toPut");

        testStudent.setAge(540);
        testStudent.setName("testName4.0-Student-toPut");
        testStudent.setId(expectedTestStudent.getId());

        testStudentwithJSON.put("name", "testName4.1-JSONStudent-toPut");
        testStudentwithJSON.put("age", 540);


        //1way
//        Gson gson = new Gson();
//        String converToJson = new Gson().toJson();
//        JSONObject expectedUpdateStudent = new JSONObject(converToJson);
//        expectedUpdateStudent.getJSONObject("id");
//
//        expectedUpdateStudent.put("name", "test");
//
//        //JSON to Java Object
//        Student student = gson.fromJson(String.valueOf(expectedUpdateStudent.getJSONObject("student")), Student.class);
//
//        //2way - Java Object to JSON
////        ObjectMapper objectMapper = new ObjectMapper();
////        objectMapper.writeValueAsString(new File("src/test/resources/json_student.json"));
//
//
// TODO
        String urlAndPath = String.format("http://localhost:" + port + "/student/update/");

        template.put(urlAndPath, expectedTestStudent);
        template.put(urlAndPath, testStudent);
        template.put(urlAndPath, expectedTestStudent2);
        template.put(urlAndPath, testStudentwithJSON);

        Student actualTestStudent1 = template.getForObject("http://localhost:" + port + "/student/get" + expectedTestStudent.getId(), Student.class);
        Student actualTestStudent1_1 = template.getForObject(String.format("http://localhost:" + port + "/student/get" + testStudent.getId()), Student.class);

        Student actualTestStudent2_1 = template.getForObject("http://localhost:" + port + "/student/get" + expectedTestStudent2.getId(), Student.class);
        Student actualTestStudent2_2 = template.getForObject(String.format("http://localhost:" + port + "/student/get" + testStudentwithJSON.getAsString("id")), Student.class);
        Assertions.assertThat(actualTestStudent1).isNotNull();
        Assertions.assertThat(actualTestStudent1.getAge()).isNotNull();

        Assertions.assertThat(actualTestStudent1_1).isNotNull();
        Assertions.assertThat(actualTestStudent1_1.getAge()).isNotNull();

        Assertions.assertThat(actualTestStudent2_1).isNotNull();
        Assertions.assertThat(actualTestStudent2_1.getAge()).isNotNull();

        Assertions.assertThat(actualTestStudent2_2).isNotNull();
        Assertions.assertThat(actualTestStudent2_2.getAge()).isNotNull();

        //exchange - for Put
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<Void> response = template.exchange(urlAndPath,
                HttpMethod.PUT,
                new HttpEntity<>(expectedTestStudent),
                Void.class
        );
        ResponseEntity<Void> response2 = template.exchange(urlAndPath,
                HttpMethod.PUT,
                new HttpEntity<>(testStudent),
                Void.class
        );
        ResponseEntity<Void> response3 = template.exchange(urlAndPath,
                HttpMethod.PUT,
                new HttpEntity<>(expectedTestStudent2),
                Void.class
        );
        ResponseEntity<Void> response4 = template.exchange(urlAndPath,
                HttpMethod.PUT,
                new HttpEntity<>(testStudentwithJSON),
                void.class
        );
        ResponseEntity<Void> response5 = template.exchange("http://localhost:" + port + "/student/update/",
                HttpMethod.PUT,
                new HttpEntity<>(new Student(111L, "0", 0), headers),
                Void.class
        );
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        Assertions.assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        Assertions.assertThat(response3.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        Assertions.assertThat(response4.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        Assertions.assertThat(response5.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        Assertions.assertThat(response5.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);


// FIXME - Можно передавать строку, но не принимает JSON - говорит что нет для него данных в "exchange", возможно чать пути spring отдает на запрос главной страницы, а остальную часть передает в тело запроса, и не ясно, какие из путей будут в url, а какие в HttpEntity, кроме тех случае когда указываем PathVariable для entity:
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);

//        ResponseEntity<Student> response = template.exchange(String.format("http://localhost:" + port + "/student/update/"), HttpMethod.PUT, new HttpEntity<>(expectedUpdateStudent), Student.class);
//
//        https://theboreddev.com/test-put-request-in-spring-java-application/amp/
//        https://medium.com/@idiotN/how-to-call-delete-method-using-resttemplate-spring-boot-4d9ffd779085
    }

    @Test
    void deleteOneStudent() throws Exception {
        template = new TestRestTemplate();
        Student testStudent = new Student();
        testStudent.setName("testName5");
        testStudent.setAge(60);
        Student expectedTestStudent = template.postForObject("http://localhost:" + port + "/student/create", testStudent, Student.class);

        String urlAndPath = String.format("http://localhost:" + port + "/student/delete/" + expectedTestStudent.getId());

        template.delete(urlAndPath, expectedTestStudent, expectedTestStudent.getId());

//        Student actualTestStudent = template.getForObject("http://localhost:" + port + "/student/get" + expectedTestStudent.getId(), Student.class);

    }
    // TODO
//    void createManyStudent() throws Exception {
}