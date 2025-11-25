package edu.ccrm.domain;

public abstract class Person {
    private int id;
    private String fullName;
    private String email;

    public Person(int id, String fullName, String email) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    // Abstract method
    public abstract String getProfile();

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + fullName + ", Email: " + email;
    }
}