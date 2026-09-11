package com.university.sms;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract Instructor class. FullTimeInstructor and PartTimeInstructor
 * override calculateSalary() differently, another example of
 * polymorphism working alongside Student's hierarchy.
 */
public abstract class Instructor extends Person {

    private String department;
    private final List<String> assignedCourseCodes = new ArrayList<>();

    public Instructor(String id, String firstName, String lastName, String email, String department) {
        super(id, firstName, lastName, email);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<String> getAssignedCourseCodes() {
        return List.copyOf(assignedCourseCodes);
    }

    public void assignCourse(String courseCode) {
        if (!assignedCourseCodes.contains(courseCode)) {
            assignedCourseCodes.add(courseCode);
        }
    }

    public void unassignCourse(String courseCode) {
        assignedCourseCodes.remove(courseCode);
    }

    /** Subclasses define pay structure (salaried vs hourly). */
    public abstract double calculateSalary();

    @Override
    public String getRole() {
        return "Instructor";
    }
}
