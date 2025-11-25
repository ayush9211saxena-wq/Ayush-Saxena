package edu.ccrm.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Student extends Person {
    private String regNo;
    private String status; // active/inactive
    private LocalDate dateJoined;
    private List<Course> enrolledCourses = new ArrayList<>();

    public Student(int id, String fullName, String email, String regNo, String status) {
        super(id, fullName, email);
        this.regNo = regNo;
        this.status = status;
        this.dateJoined = LocalDate.now();
    }

    // Getters & Setters
    public String getRegNo() { return regNo; }
    public void setRegNo(String regNo) { this.regNo = regNo; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getDateJoined() { return dateJoined; }

    public List<Course> getEnrolledCourses() { return enrolledCourses; }
    public void enrollCourse(Course course) { enrolledCourses.add(course); }
    public void unenrollCourse(Course course) { enrolledCourses.remove(course); }

    @Override
    public String getProfile() {
        return "Student Profile: " + super.toString() + ", RegNo: " + regNo + ", Status: " + status;
    }

    @Override
    public String toString() {
        return "[Student] " + getProfile();
    }
}