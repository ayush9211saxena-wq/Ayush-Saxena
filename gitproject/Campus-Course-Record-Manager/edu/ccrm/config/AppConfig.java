package edu.ccrm.config;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * AppConfig is a Singleton that stores global settings.
 */
public class AppConfig {
    private static AppConfig instance; // the one and only object

    private Properties props; // holds config key-value pairs

    private String studentFile = "students.csv";
    private String courseFile = "courses.csv";
    private String backupFolder = "backups";
    private int maxCredits = 18;

    // Private constructor to prevent new()
    private AppConfig() {
        props = new Properties();

        try {
            // Load properties if file exists
            FileInputStream fis = new FileInputStream("config.properties");
            props.load(fis);
            studentFile = props.getProperty("studentFile", studentFile);
            courseFile = props.getProperty("courseFile", courseFile);
            backupFolder = props.getProperty("backupFolder", backupFolder);
            maxCredits = Integer.parseInt(props.getProperty("maxCredits", String.valueOf(maxCredits)));

        } catch (IOException e) {
            System.out.println("⚠ No config.properties found, using defaults.");
        }
    }
    public static synchronized AppConfig getInstance() {
        if (instance == null) {
            instance = new AppConfig();
        }
        return instance;
    }
    public String getStudentFile() { return studentFile; }
    public String getCourseFile() { return courseFile; }
    public String getBackupFolder() { return backupFolder; }
    public int getMaxCredits() { return maxCredits; }
}