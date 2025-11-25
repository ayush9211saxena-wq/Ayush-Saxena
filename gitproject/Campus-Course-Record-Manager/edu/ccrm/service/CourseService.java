package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Instructor;
import edu.ccrm.domain.Semester;

import java.util.*;
import java.util.stream.Collectors;

public class CourseService {
    private Map<String, Course> courses = new HashMap<>(); // Key: course code
    public Collection<Course> getAllCourses() {
    return courses.values();
}

    // Create
    public void addCourse(Course course) {
        courses.put(course.getCode(), course);
    }

    // Read
    public Course getCourse(String code) {
        return courses.get(code);
    }

    // Delete/Deactivate (demo: remove from map)
    public void removeCourse(String code) {
        courses.remove(code);
    }

    // List all
    public void listCourses() {
        courses.values().forEach(System.out::println);
    }

    // Stream API: Search by Instructor
    public List<Course> findByInstructor(Instructor instructor) {
        return courses.values()
                .stream()
                .filter(c -> c.getInstructor().equals(instructor))
                .collect(Collectors.toList());
    }

    // Stream API: Filter by Department
    public List<Course> findByDepartment(String dept) {
        return courses.values()
                .stream()
                .filter(c -> c.getDepartment().equalsIgnoreCase(dept))
                .collect(Collectors.toList());
    }

    // Stream API: Filter by Semester
    public List<Course> findBySemester(Semester semester) {
        return courses.values()
                .stream()
                .filter(c -> c.getSemester() == semester)
                .collect(Collectors.toList());
    }
}