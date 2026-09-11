package com.university.sms;

/**
 * Abstract base class representing any person in the system
 * (a Student or an Instructor). Demonstrates encapsulation
 * (private fields with controlled access) and provides the
 * hook for polymorphism via the abstract getRole()/toString()
 * behaviour that subclasses must specialise.
 */
public abstract class Person {

    private String id;
    private String firstName;
    private String lastName;
    private String email;

    public Person(String id, String firstName, String lastName, String email) {
        this.id = id;
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
    }

    // ---- Encapsulated accessors with light validation ----

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First name cannot be empty.");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Last name cannot be empty.");
        }
        this.lastName = lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email address: " + email);
        }
        this.email = email;
    }

    /**
     * Every concrete subclass must describe its own role.
     * This is the main polymorphic hook used when printing
     * mixed lists of Person objects (e.g. Students and Instructors).
     */
    public abstract String getRole();

    @Override
    public String toString() {
        return String.format("[%s] %s (%s) - %s", getRole(), getFullName(), getId(), getEmail());
    }
}
