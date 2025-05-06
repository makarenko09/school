package ru.hogwarts.school;


import com.google.gson.Gson;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTestRestTemplateTest {
    Long testId = null;
    Student testStudent = new Student();

    Student expectedTestStudent = null;
    String testName = "testName";
    int testAge = 666;

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
        testStudent.setName("testName");
        testStudent.setAge(666);
//        testStudent.setId(null);
//            testId = testStudent.getId();
//            testStudent.setId(testId);
        setStartSettingsforCreateMethodOfStudent();
        Assertions.assertThat(expectedTestStudent).isNotNull();
    }

    void setStartSettingsforCreateMethodOfStudent() {
        expectedTestStudent = template.postForObject("http://localhost:" + port + "/student/create", testStudent, Student.class);
    }

    @Test
    void getOneStudent() {
        testStudent.setName("testName2");
        testStudent.setAge(989);
        expectedTestStudent = template.postForObject("http://localhost:" + port + "/student/create", testStudent, Student.class);

        Student responseTestGetStudent = template.getForObject("http://localhost:" + port + "/student/get" + expectedTestStudent.getId(), Student.class);
        Assertions.assertThat(responseTestGetStudent).isNotNull();
    }

    @Test
    void updateOneStudent() throws Exception {
        testStudent.setName("testName3");
        testStudent.setAge(88);
        expectedTestStudent = template.postForObject("http://localhost:" + port + "/student/create", testStudent, Student.class);

        System.out.println("StudentControllerTestRestTemplateTest.updateOneStudent");
        System.out.println(expectedTestStudent);
//
        expectedTestStudent.setAge(100);
        System.out.println(expectedTestStudent);
        //1way
//        Gson gson = new Gson();
//        String converToJson = new Gson().toJson(expectedTestStudent);
//        JSONObject expectedUpdateStudent = new JSONObject(converToJson);
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

        Student actualTestStudent = template.getForObject("http://localhost:" + port + "/student/get" + expectedTestStudent.getId(), Student.class);
        Assertions.assertThat(actualTestStudent).isNotNull();
        Assertions.assertThat(actualTestStudent.getAge()).isNotNull();
        System.out.println("StudentControllerTestRestTemplateTest.updateOneStudent");
        System.out.println(actualTestStudent);

// FIXME - Можно передавать строку, но не принимает JSON - говорит что нет для него данных в "exchange", возможно чать пути spring отдает на запрос главной страницы, а остальную часть передает в тело запроса, и не ясно, какие из путей будут в url, а какие в HttpEntity, кроме тех случае когда указываем PathVariable для entity:
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);

//        ResponseEntity<Student> response = template.exchange(String.format("http://localhost:" + port + "/student/update/"), HttpMethod.PUT, new HttpEntity<>(expectedUpdateStudent), Student.class);
//
//        https://theboreddev.com/test-put-request-in-spring-java-application/amp/
//        https://medium.com/@idiotN/how-to-call-delete-method-using-resttemplate-spring-boot-4d9ffd779085
    }
}