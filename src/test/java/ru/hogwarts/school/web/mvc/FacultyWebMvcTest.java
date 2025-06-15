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
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacultyController.class)
public class FacultyWebMvcTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @InjectMocks
    private FacultyController facultyController;
    @MockitoBean
    private FacultyService facultyService;
    @MockitoBean
    private FacultyRepository facultyRepository;
    @MockitoBean
    private StudentRepository studentRepository;

    @Test
    public void createOneFacultyTest() throws Exception {
        String name = "testName";
        final Long id = 1L;
        final String color = "testColor";

        Faculty facultyExtend = new Faculty();
        facultyExtend.setId(id);
        facultyExtend.setName(name);
        facultyExtend.setColor(color);

        JSONObject facultyJsonActual = new JSONObject();
        facultyJsonActual.put("name", name);
        facultyJsonActual.put("color", color);

        when(facultyService.createFaculty(any(Faculty.class))).thenReturn(facultyExtend);
        mockMvc.perform(post("/faculty/create")
                        .content(facultyJsonActual.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void createFacultiesTest() throws Exception {
        String name = "testName33333";
        Long id = 1L;
        String testColor = "testColor";
        Faculty facultyExtend = new Faculty();
        facultyExtend.setId(id);
        facultyExtend.setName(name);
        facultyExtend.setColor(testColor);

        String name2 = "testName253";
        Long id2 = 2L;
        String color2 = "testColor";
        Faculty facultyExtend2 = new Faculty();
        facultyExtend2.setId(id2);
        facultyExtend2.setName(name2);
        facultyExtend2.setColor(color2);

        List<Faculty> faculties = List.of(facultyExtend, facultyExtend2);

        when(facultyService.createFaculties(anyList())).thenReturn(faculties);

        String json = objectMapper.writeValueAsString(faculties);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty/create/many")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].color").value(testColor))
                .andExpect(jsonPath("$[1].id").value(id2))
                .andExpect(jsonPath("$[1].name").value(name2))
                .andExpect(jsonPath("$[1].color").value(color2));
    }

    @Test
    public void addStudentsToFacultyTest() throws Exception {
        String nameFaculty = "testFacultyName";
        Long facultyId = 55L;
        final String colorFaculty = "testFacultyColor";
        Faculty facultyExtend = new Faculty();
        facultyExtend.setId(facultyId);
        facultyExtend.setName(nameFaculty);
        facultyExtend.setColor(colorFaculty);

        String name = "testName";
        final Long id = 1L;
        final int age = 41;
        Student studentExtend = new Student();
        studentExtend.setId(id);
        studentExtend.setName(name);
        studentExtend.setAge(age);

        String name2 = "testName2";
        final Long id2 = 2L;
        final int age2 = 55;
        Student studentExtend2 = new Student();
        studentExtend2.setId(id2);
        studentExtend2.setName(name2);
        studentExtend2.setAge(age2);

        List<Student> students = List.of(studentExtend, studentExtend2);

        when(facultyRepository.findById(facultyId)).thenReturn(Optional.of(facultyExtend));
        doNothing().when(facultyService).putStudentsToFaculty(eq(facultyId), anyList());
        when(studentRepository.saveAll(anyList())).thenReturn(students);

        String studentsJson = objectMapper.writeValueAsString(students);
        mockMvc.perform(post("/faculty/add/students/{facultyId}", facultyId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentsJson))
                .andExpect(status().isOk());
    }

    @Test
    public void getFacultyTest() throws Exception {
        String name = "testName";
        final Long id = 1L;
        final String color = "testColor";

        Faculty facultyExtend = new Faculty();
        facultyExtend.setId(id);
        facultyExtend.setName(name);
        facultyExtend.setColor(color);

        when(facultyService.getFaculty(any(Long.class))).thenReturn(facultyExtend);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(facultyExtend));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/get/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void getFacultiesWitColorTest() throws Exception {
        String name = "testName";
        Long id = 1L;
        String testColor = "testColor";
        Faculty facultyExtend = new Faculty();
        facultyExtend.setId(id);
        facultyExtend.setName(name);
        facultyExtend.setColor(testColor);

        String name2 = "testName253";
        Long id2 = 2L;
        String color2 = "testColor";
        Faculty facultyExtend2 = new Faculty();
        facultyExtend2.setId(id2);
        facultyExtend2.setName(name2);
        facultyExtend2.setColor(color2);

        List<Faculty> faculties = List.of(facultyExtend, facultyExtend2);

        when(facultyService.getFacultiesWithValueColor(any(String.class))).thenReturn(faculties);
        when(facultyRepository.findAll()).thenReturn((faculties));

        String color = "testColor";
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/get/many/{color}", color)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].color").value(testColor))
                .andExpect(jsonPath("$[1].id").value(id2))
                .andExpect(jsonPath("$[1].name").value(name2))
                .andExpect(jsonPath("$[1].color").value(color2));
    }

    @Test
    public void getStudentsByFacultyTest() throws Exception {
        String name = "testName44";
        final Long id = 1L;
        final int age = 41;
        Student studentExtend = new Student();
        studentExtend.setId(id);
        studentExtend.setName(name);
        studentExtend.setAge(age);

        String name2 = "testName556";
        final Long id2 = 2L;
        final int age2 = 41;
        Student studentExtend2 = new Student();
        studentExtend2.setId(id2);
        studentExtend2.setName(name2);
        studentExtend2.setAge(age2);

        List<Student> students = List.of(studentExtend, studentExtend2);

        String nameFaculty = "testFacultyName2";
        Long facultyId = 55L;
        final String colorFaculty = "testFacultyColor3";
        Faculty facultyExtend = new Faculty();
        facultyExtend.setId(facultyId);
        facultyExtend.setName(nameFaculty);
        facultyExtend.setColor(colorFaculty);
        facultyExtend.getStudents().clear();

        facultyExtend.addStudent(studentExtend);
        facultyExtend.addStudent(studentExtend2);

        when(facultyService.getStudentsByFaculty(facultyId)).thenReturn(students);
        when(facultyRepository.findById(facultyId)).thenReturn(Optional.of(facultyExtend));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/get/students/{facultyId}", facultyId)
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
    public void updateFacultyTest() throws Exception {
        String name = "testName";
        final Long id = 2L;
        final String color = "testColor";

        Faculty facultyExtend = new Faculty();
        facultyExtend.setId(id);
        facultyExtend.setName(name);
        facultyExtend.setColor(color);

        JSONObject facultyJsonActual = new JSONObject();
        facultyJsonActual.put("name", name);
        facultyJsonActual.put("color", color);

        when(facultyService.createFaculty(any(Faculty.class))).thenReturn(facultyExtend);
        when(facultyService.getFaculty(any(Long.class))).thenReturn(facultyExtend);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(facultyExtend));

        mockMvc.perform(post("/faculty/create")
                        .content(facultyJsonActual.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        name = "UpdatedTestName";
        facultyExtend.setName(name);

        when(facultyService.updateFaculty(any(Faculty.class))).thenReturn(facultyExtend);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty/update")
                        .content(facultyJsonActual.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/get/" + id)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void deleteFacultyTest() throws Exception {
        String name = "testName";
        final Long id = 3L;
        final String color = "testColor";

        Faculty facultyExtend = new Faculty();
        facultyExtend.setId(id);
        facultyExtend.setName(name);
        facultyExtend.setColor(color);

        JSONObject facultyJsonActual = new JSONObject();
        facultyJsonActual.put("name", name);
        facultyJsonActual.put("color", color);

        when(facultyService.createFaculty(any(Faculty.class))).thenReturn(facultyExtend);
        when(facultyService.getFaculty(any(Long.class))).thenReturn(facultyExtend);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(facultyExtend));

        mockMvc.perform(post("/faculty/create")
                        .content(facultyJsonActual.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));

        when(facultyService.getFaculty(id)).thenReturn(facultyExtend);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/delete/"+id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
