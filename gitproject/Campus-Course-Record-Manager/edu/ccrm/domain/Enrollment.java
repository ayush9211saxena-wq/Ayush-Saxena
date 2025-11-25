package edu.ccrm.domain;

import java.time.LocalDate;

public class Enrollment {
    private Student student;
    private Course course;
    private Grade grade;
    private Semester semester;
    private LocalDate enrollmentDate;

    public Enrollment(Student student, Course course, Semester semester) {
        this.student = student;
        this.course = course;
        this.semester = semester;
        this.enrollmentDate = LocalDate.now();
    }

    public Student getStudent() { return student; }
    public Course getCourse() { return course; }
    public Grade getGrade() { return grade; }
    public void setGrade(Grade grade) { this.grade = grade; }
    public Semester getSemester() { return semester; }
    public LocalDate getEnrollmentDate() { return enrollmentDate; }

    @Override
    public String toString() {
        return "Enrollment[Student=" + student.getRegNo() + ", Course=" + course.getCode() +
                ", Grade=" + grade + ", Semester=" + semester + "]";
    }
}