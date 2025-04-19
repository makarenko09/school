package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private final Map<Long, Faculty> facultiesMap = new HashMap<>();
    private long lastId = 0L;

    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(++lastId);
        facultiesMap.put((this.lastId), faculty);
        return faculty;
    }

    public Faculty getFaculty(Long id) {
        if (facultiesMap.get(id) != null) {
        return facultiesMap.get(id);

        } else {
            throw new NoSuchSomeObjectException(" - " + id + " does not exist");
        }
    }

    public Faculty updateFaculty(Faculty faculty) {
        Faculty someObj = facultiesMap.values().stream().filter(obj -> Objects.equals(obj.getId(), faculty.getId())).map(obj -> obj.newObject(faculty.getId(), faculty.getName(), faculty.getColor())).findFirst().orElseThrow(() -> new NoSuchSomeObjectException(" - " + faculty + " does not exist"));
        facultiesMap.put(faculty.getId(), someObj);
        return facultiesMap.get(faculty.getId());
    }

    public Faculty deleteFaculty(Long id) {
        if (facultiesMap.get(id) != null) {
            return facultiesMap.remove(id);
        } else {
            throw new NoSuchSomeObjectException(" - " + id + " does not exist");
        }
    }

    public Collection<Faculty> getFacultiesWithValueColor(String color) {
        return facultiesMap.values().stream()
                .filter(obj -> color.toLowerCase().equals(obj.getColor()))
                .collect(Collectors.toList());
    }
}
