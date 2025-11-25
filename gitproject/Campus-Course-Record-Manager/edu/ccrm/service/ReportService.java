package edu.ccrm.service;

import edu.ccrm.domain.*;

import java.util.*;
import java.util.stream.Collectors;

public class ReportService {
    private final EnrollmentService enrollmentService;

    public ReportService(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    // ✅ 1. Top N Students by GPA
    public void printTopStudents(int n, Collection<Student> allStudents) {
        System.out.println("\n--- Top " + n + " Students by GPA ---");

        allStudents.stream()
            .map(s -> Map.entry(s, enrollmentService.computeGPA(s)))
            .filter(e -> e.getValue() > 0) // only enrolled students
            .sorted((a, b) -> Double.compare(b.getValue(), a.getValue()))
            .limit(n)
            .forEach(e -> 
                System.out.printf("%s (RegNo: %s) -> GPA: %.2f%n",
                    e.getKey().getFullName(),
                    e.getKey().getRegNo(),
                    e.getValue())
            );
    }

    // ✅ 2. GPA Distribution (Histogram)
    public void printGPADistribution(Collection<Student> allStudents) {
        System.out.println("\n--- GPA Distribution ---");

        Map<String, Long> distribution = allStudents.stream()
            .map(enrollmentService::computeGPA)
            .map(gpa -> {
                if (gpa >= 3.5) return "3.5 - 4.0";
                else if (gpa >= 3.0) return "3.0 - 3.49";
                else if (gpa >= 2.0) return "2.0 - 2.99";
                else if (gpa > 0) return "< 2.0";
                else return "No GPA";
            })
            .collect(Collectors.groupingBy(x -> x, Collectors.counting()));

        distribution.forEach((range, count) -> 
            System.out.println(range + " : " + count + " students"));
    }

    // ✅ 3. Average GPA Across All Students
    public void printAverageGPA(Collection<Student> allStudents) {
        double avg = allStudents.stream()
            .mapToDouble(enrollmentService::computeGPA)
            .filter(gpa -> gpa > 0)
            .average()
            .orElse(0.0);

        System.out.printf("\n--- Average GPA of All Students: %.2f ---\n", avg);
    }
}