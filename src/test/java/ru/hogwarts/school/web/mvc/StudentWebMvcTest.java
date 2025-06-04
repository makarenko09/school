package ru.hogwarts.school.web.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class StudentWebMvcTest {
    @MockitoBean
    private FacultyService facultyService;
    @MockitoBean
    private FacultyRepository facultyRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private StudentRepository studentRepository;
    @MockitoBean
    private StudentService studentService;
    @InjectMocks
    private StudentController studentController;

    @Test
    public void saveOneStudentTest() throws Exception {
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

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student/create")
                        .content(studentJsonActual.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }
@Test
public void getOneStudentTest() throws Exception {
    String name = "testName";
    final Long id = 1L;
    final int age = 41;Student studentExtend = new Student();
    studentExtend.setId(id);
    studentExtend.setName(name);
    studentExtend.setAge(age);
    when(studentService.getStudent(any(Long.class))).thenReturn(studentExtend);
    when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(studentExtend));
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
public void saveManyStudentsTest() throws Exception {
        String name = "testName1111111";
    final Long id = 1L;
    final int age = 41;

        Student studentExtend = new Student(id, name, age);

        String name2 = "testName222222222222";
        final Long id2 = 2L;
        final int age2 = 52;

        Student studentExtend2 = new Student(id2, name2, age2);

    List<Student> students = List.of(studentExtend, studentExtend2);
        when(studentService.createStudents(anyList())).thenReturn(students);
        String json = objectMapper.writeValueAsString(List.of(studentExtend, studentExtend2));

        mockMvc.perform(MockMvcRequestBuilders
                    .post("/student/create/many")
                        .content(json)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].age").value(age))
                .andExpect(jsonPath("$[1].id").value(id2))
                .andExpect(jsonPath("$[1].name").value(name2))
                .andExpect(jsonPath("$[1].age").value(age2));
}
    @Test
    void getFacultyByStudentTest() throws Exception {
        // --- Подготовка факультета ---
        String nameFaculty = "testFacultyName";
        Long facultyId = 55L;
        final String colorFaculty = "testFacultyColor";

        String name = "testName";
        final Long idStudent = 1L;
        final int age = 41;

        Faculty facultyExtend = new Faculty();
        facultyExtend.setId(facultyId);
        facultyExtend.setName(nameFaculty);
        facultyExtend.setColor(colorFaculty);


        Student studentExtend = new Student();
        studentExtend.setId(idStudent);
        studentExtend.setName(name);
        studentExtend.setAge(age);
    studentExtend.setFaculty(facultyExtend); // связываем

        // --- Моки ---
        when(studentService.getStudent(anyLong())).thenReturn(studentExtend);

        // --- Тест запроса ---
        mockMvc.perform(get("/student/get/faculty/{idStudent}", idStudent)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(facultyId))
                .andExpect(jsonPath("$.name").value(facultyExtend.getName()))
                .andExpect(jsonPath("$.color").value(facultyExtend.getColor()));
    }
    @Test
    public void getStudentsWithAgeBetweenTest() throws Exception {
        String name = "testName";
        Long id = 5L;
        final int age = 41;

        String name2 = "testName2";
        Long id2 = 6L;
        final int age2 = 52;

        Student studentExtend = new Student();
        studentExtend.setId(id);
        studentExtend.setName(name);
        studentExtend.setAge(age);

        Student studentExtend2 = new Student();
        studentExtend2.setId(id2);
        studentExtend2.setName(name2);
        studentExtend2.setAge(age2);

        List<Student> students = List.of(studentExtend, studentExtend2);

        when(studentService.getStudentsAgeBetween(41, 52)).thenReturn(students);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/get/many?min=41&max=52")  // ✅ исправлен URL
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].age").value(age))
                .andExpect(jsonPath("$[1].id").value(id2))
                .andExpect(jsonPath("$[1].name").value(name2))
                .andExpect(jsonPath("$[1].age").value(age2));
    }
    @Test
    public void getStudentsWithAge() throws Exception {
        String name = "testName";
        final Long id = 1L;
        final int age = 41;
        String name2 = "testName2";
        final Long id2 = 2L;
        final int age2 = 41;

        Student studentExtend = new Student();
        studentExtend.setId(id);
        studentExtend.setName(name);
        studentExtend.setAge(age);

        Student studentExtend2 = new Student();
        studentExtend2.setId(id2);
        studentExtend2.setName(name2);
        studentExtend2.setAge(age2);

        List<Student> students = List.of(studentExtend, studentExtend2);

        when(studentService.getStudentsWithValueAge(age)).thenReturn(students);
        when(studentRepository.findAll()).thenReturn(students);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/get/many/41") // ✅ исправлено
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].age").value(age))
                .andExpect(jsonPath("$[1].id").value(id2))
                .andExpect(jsonPath("$[1].name").value(name2))
                .andExpect(jsonPath("$[1].age").value(age2));
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
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));


        mockMvc.perform(get("/student/get/2")
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
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
