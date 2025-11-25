package edu.ccrm.service;

import edu.ccrm.config.AppConfig;
import edu.ccrm.domain.*;
import edu.ccrm.service.exceptions.*;

import java.util.*;

public class EnrollmentService {
    private List<Enrollment> enrollments = new ArrayList<>();

    // Enroll student with rules
    public void enrollStudent(Student student, Course course, Semester semester) 
            throws DuplicateEnrollmentException, MaxCreditLimitExceededException {

        // ✅ 1. Check for duplicate enrollment
        for (Enrollment e : enrollments) {
            if (e.getStudent().equals(student) && 
                e.getCourse().equals(course) &&
                e.getSemester() == semester) {
                throw new DuplicateEnrollmentException(
                    "Student " + student.getRegNo() + " is already enrolled in " + course.getCode()
                );
            }
        }

        // ✅ 2. Check credit limit (get limit from AppConfig Singleton)
        int totalCredits = enrollments.stream()
                .filter(e -> e.getStudent().equals(student) && e.getSemester() == semester)
                .mapToInt(e -> e.getCourse().getCredits())
                .sum();

        int maxCredits = AppConfig.getInstance().getMaxCredits();

        if (totalCredits + course.getCredits() > maxCredits) {
            throw new MaxCreditLimitExceededException(
                "Student " + student.getRegNo() + " exceeds max " + maxCredits + 
                " credits with course " + course.getCode()
            );
        }

        // ✅ 3. If all checks pass, enroll student
        Enrollment e = new Enrollment(student, course, semester);
        enrollments.add(e);
        student.enrollCourse(course); 
    }

    // Unenroll student
    public void unenrollStudent(Student student, Course course) {
        enrollments.removeIf(e -> e.getStudent().equals(student) && e.getCourse().equals(course));
        student.unenrollCourse(course);
    }

    // Assign Grade
    public void assignGrade(Student student, Course course, Grade grade) {
        for (Enrollment e : enrollments) {
            if (e.getStudent().equals(student) && e.getCourse().equals(course)) {
                e.setGrade(grade);
            }
        }
    }

    // Compute GPA
    public double computeGPA(Student student) {
        double totalPoints = 0;
        int totalCredits = 0;

        for (Enrollment e : enrollments) {
            if (e.getStudent().equals(student) && e.getGrade() != null) {
                totalPoints += e.getGrade().getPoints() * e.getCourse().getCredits();
                totalCredits += e.getCourse().getCredits();
            }
        }
        return totalCredits > 0 ? totalPoints / totalCredits : 0.0;
    }

    // Print Transcript
    public void printTranscript(Student student) {
        System.out.println("Transcript for " + student.getFullName() + ":");

        for (Enrollment e : enrollments) {
            if (e.getStudent().equals(student)) {
                System.out.println(e);
            }
        }

        double gpa = computeGPA(student);
        System.out.printf("Overall GPA: %.2f%n", gpa);
    }
}