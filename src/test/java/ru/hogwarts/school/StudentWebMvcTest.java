package ru.hogwarts.school;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.NoSuchSomeObjectException;
import ru.hogwarts.school.service.StudentService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class StudentWebMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentRepository studentRepository;

    //    @MockitoBean
    @MockitoBean
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @Test
    public void saveAndGetStudentTest() throws Exception {
        String name = "testName";
        final Long id = 1L;
        final int age = 41;
        JSONObject studentJsonActual = new JSONObject();
        studentJsonActual.put("name", name);
        studentJsonActual.put("age", age);

        Student studentExtend = new Student();
        studentExtend.setId(id);
        studentExtend.setName(name);
        studentExtend.setAge(age);

        when(studentService.createStudent(any(Student.class))).thenReturn(studentExtend);
        when(studentService.getStudent(any(Long.class))).thenReturn(studentExtend);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(studentExtend));
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student/create")
                        .content(studentJsonActual.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/get/1")
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    public void updateStudentTest() throws Exception {
        String name = "testName";
        final Long id = 2L;
        final int age = 41;
        JSONObject studentJsonActual = new JSONObject();
        studentJsonActual.put("name", name);
        studentJsonActual.put("age", age);

        Student studentExtend = new Student();
        studentExtend.setId(id);
        studentExtend.setName(name);
        studentExtend.setAge(age);

        when(studentService.createStudent(any(Student.class))).thenReturn(studentExtend);
        when(studentService.getStudent(any(Long.class))).thenReturn(studentExtend);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(studentExtend));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student/create")
                        .content(studentJsonActual.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        name = "UpdatedTestName";
        studentExtend.setName(name);
        when(studentService.updateStudent(any(Student.class))).thenReturn(studentExtend);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student/update")
                        .content(studentJsonActual.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));


        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/get/2")
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));

    }

    @Test
    public void deleteStudentTest() throws Exception {
        String name = "testName";
        final Long id = 3L;
        final int age = 41;
        JSONObject studentJsonActual = new JSONObject();
        studentJsonActual.put("name", name);
        studentJsonActual.put("age", age);

        Student studentExtend = new Student();
        studentExtend.setId(id);
        studentExtend.setName(name);
        studentExtend.setAge(age);

        when(studentService.createStudent(any(Student.class))).thenReturn(studentExtend);
        when(studentService.getStudent(any(Long.class))).thenReturn(studentExtend);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(studentExtend));
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student/create")
                        .content(studentJsonActual.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));

//        verify(studentRepository).delete(id);

        when(studentService.deleteStudent(any(Long.class))).thenReturn(studentExtend);
        when(studentService.getStudent(id)).thenReturn(studentExtend);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/delete/3", 3L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}
