package edu.ccrm.cli;

import edu.ccrm.domain.*;
import edu.ccrm.service.*;
import edu.ccrm.io.*;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    // Core services
    private static final StudentService studentService = new StudentService();
    private static final CourseService courseService = new CourseService();
    private static final EnrollmentService enrollmentService = new EnrollmentService();
    private static final ReportService reportService = new ReportService(enrollmentService);

    // File I/O services
    private static final ImportExportService ioService = new ImportExportService(studentService, courseService);
    private static final BackupService backupService = new BackupService();

    // Color codes (only if supported)
    private static final boolean USE_ANSI = supportsAnsi();
    private static final String RESET  = USE_ANSI ? "\u001B[0m"  : "";
    private static final String GREEN  = USE_ANSI ? "\u001B[32m" : "";
    private static final String BLUE   = USE_ANSI ? "\u001B[34m" : "";
    private static final String CYAN   = USE_ANSI ? "\u001B[36m" : "";
    private static final String YELLOW = USE_ANSI ? "\u001B[33m" : "";
    private static final String RED    = USE_ANSI ? "\u001B[31m" : "";

    public static void main(String[] args) {
        boolean running = true;

        banner();

        while (running) {
            printMainMenu();

            System.out.print(YELLOW + "Choose option: " + RESET);
            String input = scanner.nextLine();
            int choice = input.matches("\\d+") ? Integer.parseInt(input) : -1;

            switch (choice) {
                case 1 -> studentMenu();
                case 2 -> courseMenu();
                case 3 -> enrollmentMenu();
                case 4 -> fileMenu();
                case 5 -> reportsMenu();
                case 6 -> {
                    System.out.println(GREEN + "Exiting... have a great day at CCRM! 🚀" + RESET);
                    running = false;
                }
                default -> System.out.println(RED + "Invalid choice. Please try again!" + RESET);
            }
        }
    }

    // 🎨 Banner
    private static void banner() {
        System.out.println(CYAN +
                "=============================================\n" +
                "   Welcome to Campus Course & Records Manager \n" +
                "=============================================" +
                RESET);
    }

    // Main Menu
    private static void printMainMenu() {
        System.out.println(BLUE + "\n--- MAIN MENU ---" + RESET);
        System.out.println("1. Manage Students");
        System.out.println("2. Manage Courses");
        System.out.println("3. Enrollment & Grading");
        System.out.println("4. File Operations (Import/Export/Backup)");
        System.out.println("5. Reports & Analytics");
        System.out.println("6. Exit");
    }

    // ✅ Student Menu
    private static void studentMenu() {
        System.out.println(BLUE + "\n--- STUDENT MANAGEMENT ---" + RESET);
        System.out.println("1. Add Student");
        System.out.println("2. List Students");
        System.out.println("3. Update Status");
        System.out.println("4. Back");

        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1 -> {
                System.out.print("Enter ID: ");
                int id = Integer.parseInt(scanner.nextLine());

                System.out.print("Full Name: ");
                String name = scanner.nextLine();

                System.out.print("Email: ");
                String email = scanner.nextLine();

                System.out.print("RegNo: ");
                String regNo = scanner.nextLine();

                Student s = new Student(id, name, email, regNo, "active");
                studentService.addStudent(s);

                System.out.println(GREEN + "✅ Student added successfully!" + RESET);
            }
            case 2 -> studentService.listStudents();
            case 3 -> {
                System.out.print("Enter RegNo: ");
                String regNo = scanner.nextLine();
                System.out.print("New Status (active/inactive): ");
                String status = scanner.nextLine();
                studentService.updateStatus(regNo, status);
                System.out.println(GREEN + "✅ Status updated!" + RESET);
            }
            case 4 -> {} // back
            default -> System.out.println(RED + "Invalid choice." + RESET);
        }
    }

    // ✅ Course Menu
    private static void courseMenu() {
        System.out.println(BLUE + "\n--- COURSE MANAGEMENT ---" + RESET);
        System.out.println("1. Add Course");
        System.out.println("2. List Courses");
        System.out.println("3. Back");

        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1 -> {
                System.out.print("Course Code: ");
                String code = scanner.nextLine();
                System.out.print("Title: ");
                String title = scanner.nextLine();
                System.out.print("Credits: ");
                int credits = Integer.parseInt(scanner.nextLine());
                System.out.print("Department: ");
                String dept = scanner.nextLine();

                Course c = new Course(code, title, credits, null, Semester.SPRING, dept);
                courseService.addCourse(c);
                System.out.println(GREEN + "✅ Course added!" + RESET);
            }
            case 2 -> courseService.listCourses();
            case 3 -> {} // back
            default -> System.out.println(RED + "Invalid choice." + RESET);
        }
    }

    // ✅ Enrollment Menu
    private static void enrollmentMenu() {
        System.out.println(BLUE + "\n--- ENROLLMENT MANAGEMENT ---" + RESET);
        System.out.println("1. Enroll Student");
        System.out.println("2. Assign Grade");
        System.out.println("3. Print Transcript");
        System.out.println("4. Back");

        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1 -> {
                System.out.print("Enter RegNo: ");
                String regNo = scanner.nextLine();
                System.out.print("Enter Course Code: ");
                String code = scanner.nextLine();

                Student s = studentService.getStudent(regNo);
                Course c = courseService.getCourse(code);
                if (s != null && c != null) {
                    try {
                        enrollmentService.enrollStudent(s, c, Semester.SPRING);
                        System.out.println(GREEN + "✅ Student enrolled!" + RESET);
                    } catch (edu.ccrm.service.exceptions.DuplicateEnrollmentException e) {
                        System.out.println(RED + "⚠ " + e.getMessage() + RESET);
                    } catch (edu.ccrm.service.exceptions.MaxCreditLimitExceededException e) {
                        System.out.println(RED + "⚠ " + e.getMessage() + RESET);
                    }
                } else {
                    System.out.println(RED + "⚠ Student or Course not found." + RESET);
                }
            }
            case 2 -> {
                System.out.print("RegNo: ");
                String regNo = scanner.nextLine();
                System.out.print("Course Code: ");
                String code = scanner.nextLine();
                System.out.print("Grade (A/B/C/D/F): ");
                String gradeInput = scanner.nextLine().toUpperCase();

                try {
                    Grade grade = Grade.valueOf(gradeInput);
                    enrollmentService.assignGrade(studentService.getStudent(regNo),
                                                  courseService.getCourse(code),
                                                  grade);
                    System.out.println(GREEN + "✅ Grade recorded!" + RESET);
                } catch (IllegalArgumentException e) {
                    System.out.println(RED + "⚠ Invalid grade input!" + RESET);
                }
            }
            case 3 -> {
                System.out.print("Enter RegNo: ");
                String regNo = scanner.nextLine();
                Student s = studentService.getStudent(regNo);
                if (s != null) {
                    enrollmentService.printTranscript(s);
                } else {
                    System.out.println(RED + "⚠ Student not found." + RESET);
                }
            }
            case 4 -> {} // back
            default -> System.out.println(RED + "Invalid choice." + RESET);
        }
    }

    // ✅ File Menu
    private static void fileMenu() {
        System.out.println(BLUE + "\n--- FILE OPERATIONS ---" + RESET);
        System.out.println("1. Export Students to CSV");
        System.out.println("2. Import Students from CSV");
        System.out.println("3. Export Courses to CSV");
        System.out.println("4. Import Courses from CSV");
        System.out.println("5. Backup Data");
        System.out.println("6. Back");

        int choice = Integer.parseInt(scanner.nextLine());

        try {
            switch (choice) {
                case 1 -> {
                    String studentFile = edu.ccrm.config.AppConfig.getInstance().getStudentFile();
                    ioService.exportStudents(studentFile);;
                    System.out.println(GREEN + "✅ Students exported to students.csv" + RESET);
                }
                case 2 -> {
                    ioService.importStudents("students.csv");
                    System.out.println(GREEN + "✅ Students imported from students.csv" + RESET);
                }
                case 3 -> {
                    String courseFile = edu.ccrm.config.AppConfig.getInstance().getCourseFile();
                    ioService.exportCourses(courseFile);
                    System.out.println(GREEN + "✅ Courses exported to courses.csv" + RESET);
                }
                case 4 -> {
                    ioService.importCourses("courses.csv");
                    System.out.println(GREEN + "✅ Courses imported from courses.csv" + RESET);
                }
                case 5 -> {
                    String backupFolder = edu.ccrm.config.AppConfig.getInstance().getBackupFolder();
                    backupService.backup(".", backupFolder);
                    System.out.println(GREEN + "✅ Backup created successfully!" + RESET);
                }
                case 6 -> { return; }
                default -> System.out.println(RED + "Invalid choice." + RESET);
            }
        } catch (Exception e) {
            System.out.println(RED + "⚠ Error while handling files: " + e.getMessage() + RESET);
        }
    }
    private static void reportsMenu() {
        System.out.println(BLUE + "\n--- REPORTS & ANALYTICS ---" + RESET);
        System.out.println("1. Top N Students by GPA");
        System.out.println("2. GPA Distribution");
        System.out.println("3. Average GPA");
        System.out.println("4. Back");

        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1 -> {
                System.out.print("Enter N (top how many): ");
                int n = Integer.parseInt(scanner.nextLine());
                reportService.printTopStudents(n, studentService.getAllStudents());
            }
            case 2 -> reportService.printGPADistribution(studentService.getAllStudents());
            case 3 -> reportService.printAverageGPA(studentService.getAllStudents());
            case 4 -> { return; } // back
            default -> System.out.println(RED + "Invalid choice." + RESET);
        }
    }
    // Detect support for ANSI escape codes
    private static boolean supportsAnsi() {
        String os = System.getProperty("os.name").toLowerCase();
        return !(os.contains("win") && System.console() != null && !System.console().toString().contains("ANSICON"));
    }
}