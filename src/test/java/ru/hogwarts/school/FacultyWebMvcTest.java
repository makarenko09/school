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
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacultyController.class)
public class FacultyWebMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FacultyRepository facultyRepository;

    @MockitoBean
    private FacultyService facultyService;

    @InjectMocks
    private FacultyController facultyController;

    @Test
    public void saveAndGetFacultyTest() throws Exception {
        String name = "testName";
        final Long id = 1L;
        final String color = "testColor";
        JSONObject facultyJsonActual = new JSONObject();
        facultyJsonActual.put("name", name);
        facultyJsonActual.put("color", color);

        Faculty facultyExtend = new Faculty();
        facultyExtend.setId(id);
        facultyExtend.setName(name);
        facultyExtend.setColor(color);

        when(facultyService.createFaculty(any(Faculty.class))).thenReturn(facultyExtend);
        when(facultyService.getFaculty(any(Long.class))).thenReturn(facultyExtend);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(facultyExtend));
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty/create")
                        .content(facultyJsonActual.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/get/1")
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void updateFacultyTest() throws Exception {
        String name = "testName";
        final Long id = 2L;
        final String color = "testColor";
        JSONObject facultyJsonActual = new JSONObject();
        facultyJsonActual.put("name", name);
        facultyJsonActual.put("color", color);

        Faculty facultyExtend = new Faculty();
        facultyExtend.setId(id);
        facultyExtend.setName(name);
        facultyExtend.setColor(color);

        when(facultyService.createFaculty(any(Faculty.class))).thenReturn(facultyExtend);
        when(facultyService.getFaculty(any(Long.class))).thenReturn(facultyExtend);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(facultyExtend));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty/create")
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
                        .get("/faculty/get/2")
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
        JSONObject facultyJsonActual = new JSONObject();
        facultyJsonActual.put("name", name);
        facultyJsonActual.put("color", color);

        Faculty facultyExtend = new Faculty();
        facultyExtend.setId(id);
        facultyExtend.setName(name);
        facultyExtend.setColor(color);

        when(facultyService.createFaculty(any(Faculty.class))).thenReturn(facultyExtend);
        when(facultyService.getFaculty(any(Long.class))).thenReturn(facultyExtend);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(facultyExtend));
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty/create")
                        .content(facultyJsonActual.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));

//        verify(facultyRepository).delete(id);

        when(facultyService.getFaculty(id)).thenReturn(facultyExtend);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/delete/3", 3L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
