package edu.ccrm.service;

import edu.ccrm.domain.Student;
import java.util.*;

public class StudentService {
    private Map<String, Student> students = new HashMap<>(); // Key: regNo
    public Collection<Student> getAllStudents() {
    return students.values();
}

    // Create
    public void addStudent(Student student) {
        students.put(student.getRegNo(), student);
    }
    
    // Read
    public Student getStudent(String regNo) {
        return students.get(regNo);
    }

    // Update
    public void updateStatus(String regNo, String status) {
        Student s = students.get(regNo);
        if (s != null) {
            s.setStatus(status);
        }
    }

    // Delete/Deactivate
    public void deactivateStudent(String regNo) {
        Student s = students.get(regNo);
        if (s != null) {
            s.setStatus("inactive");
        }
    }

    // List all
    public void listStudents() {
        students.values().forEach(System.out::println);
    }

    // Print profile
    public void printProfile(String regNo) {
        Student s = students.get(regNo);
        if (s != null) {
            System.out.println(s.getProfile());
        } else {
            System.out.println("Student not found.");
        }
    }
}