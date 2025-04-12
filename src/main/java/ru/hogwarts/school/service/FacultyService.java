package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.HashMap;
import java.util.Map;
@Service
public class FacultyService {
    private final Map<Long, Faculty> facultiesMap = new HashMap<>();
    private Long lastId = 0L;

    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(++lastId);
        facultiesMap.put((this.lastId), faculty);
        return faculty;
    }

    public Faculty getFaculty(Long id) {
        return facultiesMap.get(id);
    }

    public Faculty updateFaculty(Faculty faculty) {
        facultiesMap.replace(faculty.getId(), faculty);
        return faculty;
    }

    public Faculty deleteFaculty(Long id) {
        return facultiesMap.remove(id);
    }
}
