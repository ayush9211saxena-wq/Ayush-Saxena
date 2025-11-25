# Ayush-Saxena
Campus Course & Records Manager (CCRM)
📋 Project Overview:
A comprehensive console-based Java application for managing academic records, including students, courses, enrollments, and grading systems. Built with modern Java SE features, demonstrating OOP principles, design patterns, exception handling, Streams, recursion, and CLI workflows.

🎯 Key Features:
Student Management: Complete CRUD operations for student records
Course Catalog: Manage course offerings with credits and departments
Enrollment System: Handle course registrations with validation rules
Grading & Transcripts: Assign grades and generate academic transcripts
File Operations: Import/export data in CSV format
Backup System: Automated timestamped backups
Analytics: GPA reports, top performers, and distribution analysis
Exception Handling: Comprehensive error management

🏗️ Architecture:
Modular Design: Separated into domain, service, and I/O layers
Object-Oriented: Implements OOP principles and design patterns
Console Interface: User-friendly CLI with color-coded menus
Configuration Management: Singleton pattern for app settings

🚀 How to Run
Prerequisites:
Java JDK 11 or higher (17+ recommended)
Command-line terminal (PowerShell, Command Prompt, or Terminal)
Build Tool: Plain javac / java (no external dependencies).
Run The Application:
# Run program
java edu.ccrm.cli.Main

Sample Workflow:
Start the application from main menu
Add students through "Manage Students" → "Add Student"
Create courses via "Manage Courses" → "Add Course"
Enroll students in courses with automatic validation
Assign grades and generate transcripts
Export data or generate reports

📚 Java Evolution
1996: JDK 1.0 released, establishing "Write Once, Run Anywhere"
1997: JDK 1.1 introduced inner classes, JavaBeans
1998: Java 2 Platform (J2SE) with Collections Framework, Swing
2004: J2SE 5.0 (Java 5) major update with generics
2006: JDBC 4.0 (Java 6) Scripting support
2011: Java 7 with Try-with-resources, NIO.2 file APIs
2014: Java 8 with Streams API, Lambdas, default methods
2017: Java 9 introduces Module system (JPMS), JShell
2018: Java 11 (LTS) which switch to 6-month release cycle
2021+: Java 17 (LTS) with Records, Sealed classes, pattern matching
Ongoing: Switch expressions, Project Loom (virtual threads).
Java Platform Editions (Java ME vs SE vs EE)

☕ Java SE (Standard Edition):
Purpose: Core Java platform for desktop and server applications
Features: Language fundamentals, collections, I/O, networking
Usage: Desktop apps, CLI tools, educational projects
Example: This CCRM project is built on Java SE

🌐 Java EE (Enterprise Edition):
Purpose: Enterprise-level applications and web services
Features: Servlets, JSP, EJB, JPA, web services
Usage: Large-scale web applications, enterprise systems
Example: University portal with web-based registration

📱 Java ME (Micro Edition):
Purpose: Mobile and embedded devices
Features: Limited APIs for constrained environments
Usage: Feature phones, IoT devices, embedded systems
Example: Old Nokia mobile applications

🔧 Java Platform Components
JVM (Java Virtual Machine):
Role: Executes Java bytecode
Function: Platform independence through bytecode interpretation
Features: Garbage collection, security sandbox, JIT compilation
Motto: "Write Once, Run Anywhere"

JRE (Java Runtime Environment):
Components: JVM + Core Libraries (java.lang, java.util, java.io)
Purpose: Run existing Java applications
Users: End-users who need to execute Java programs
Analogy: Media player for Java applications

JDK (Java Development Kit):
Components: JRE + Development Tools (javac, javadoc, debuggers)
Purpose: Develop and compile Java applications
Users: Java developers and programmers
Formula: JDK = JRE + Development Tools

Mapping Table (Syllabus → Code):
Syllabus Topic	Where Implemented (File / Class / Method)
Encapsulation	All domain classes (private fields + getters/setters, e.g., Student.java, Course.java)
Inheritance	Person.java (abstract) → extended by Student.java, Instructor.java
Abstraction	Person.java (abstract class with abstract method getRole())
Polymorphism	Overridden getRole(), toString() in Student.java & Instructor.java
Interfaces	StudentService.java, CourseService.java,EnrollmentService.java,ReportService.java etc.
Singleton Pattern	AppConfig.java (ensures single instance for storing students & courses)
Custom Exceptions	DuplicateEnrollmentException.java, MaxCreditLimitExceededException.java
Exception Handling	CampusApp.java (try/catch in enrollment, import/export)
Checked & Unchecked Exceptions	Service layer throws custom exceptions; CLI catches & prints
Date/Time API	Student.java (admissionDate as LocalDate), BackupService.java (timestamp folders)
Enums	Grade.java (stores points), Semester.java (SPRING, SUMMER, FALL, WINTER)
Streams / Lambdas	CourseServiceImpl.java (filtering by instructor/department), CampusApp.gpaReportUI()
Recursion	BackupService.getDirSize() and util/RecursionUtils.java
File I/O (NIO.2)	ImportExportService.java, BackupService.java (Files, Paths APIs)
Anonymous Inner Class	Example comparator in CLI for sorting students
Assertions	Course.java constructor (assert credits > 0 && credits <= 18)
Arrays & Array Utilities	courseservice.java, sorting examples with Arrays.sort()

🧪 Notes on Enabling Assertions:
Assertions are used in the project to enforce invariants.
For example, in Course.java constructor:

assert credits > 0 && credits <= 18 : "Credits must be between 1 and 18";
Design Patterns Used:
Singleton: AppConfig for centralized configuration
Service Layer: Separate service classes for each domain
Factory: Object creation patterns in service classes
DTO: Data transfer objects for file operations

📁 Project Structure Deep Dive
Campus-Course-Record-Manager/
├── src/
│   ├── edu/ccrm/
│   │   ├── cli/           # Command-line interface
│   │   ├── config/        # Configuration management
│   │   ├── domain/        # Business entities
│   │   ├── io/           # File operations
│   │   └── service/      # Business logic
├── students.csv          # Sample student data
├── courses.csv          # Sample course catalog
└── README.md           # Project documentation
🔮 Future Enhancements
Potential improvements for the project:

Database integration with JDBC
Web interface using Spring Boot
REST API for external integrations
Advanced reporting with charts
User authentication and authorization
Batch processing for large datasets
