package edu.ccrm.io;

import edu.ccrm.domain.*;
import edu.ccrm.service.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ImportExportService {

    private final StudentService studentService;
    private final CourseService courseService;

    public ImportExportService(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    // ✅ Export Students to CSV
    public void exportStudents(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (Student s : studentService.getAllStudents()) {
                writer.write(s.getRegNo() + "," + s.getFullName() + "," 
                           + s.getEmail() + "," + s.getStatus());
                writer.newLine();
            }
        }
    }

    // ✅ Import Students from CSV
    public void importStudents(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    Student s = new Student(
                            new Random().nextInt(1000), // dummy ID
                            parts[1], parts[2], parts[0], parts[3]);
                    studentService.addStudent(s);
                }
            }
        }
    }

    // ✅ Export Courses to CSV
    public void exportCourses(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (Course c : courseService.getAllCourses()) {
                writer.write(c.getCode() + "," + c.getTitle() + "," 
                           + c.getCredits() + "," + c.getDepartment());
                writer.newLine();
            }
        }
    }

    // ✅ Import Courses from CSV
    public void importCourses(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    Course c = new Course(parts[0], parts[1],
                            Integer.parseInt(parts[2]), null,
                            Semester.SPRING, parts[3]);
                    courseService.addCourse(c);
                }
            }
        }
    }
}