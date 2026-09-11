package com.university.sms;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a course offering. Holds its own roster of enrolled
 * student IDs and a reference to the assigned instructor, with
 * capacity enforcement handled internally (encapsulation).
 */
public class Course {

    private final String courseCode;
    private String courseName;
    private int credits;
    private int capacity;
    private Instructor instructor;
    private final List<String> enrolledStudentIds = new ArrayList<>();

    public Course(String courseCode, String courseName, int credits, int capacity) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
        this.capacity = capacity;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        if (credits <= 0) throw new IllegalArgumentException("Credits must be positive.");
        this.credits = credits;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        if (capacity < enrolledStudentIds.size()) {
            throw new IllegalArgumentException("New capacity is below current enrolment.");
        }
        this.capacity = capacity;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void assignInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public List<String> getEnrolledStudentIds() {
        return List.copyOf(enrolledStudentIds); // defensive copy - protects internal state
    }

    public boolean isFull() {
        return enrolledStudentIds.size() >= capacity;
    }

    public void enrollStudent(String studentId) {
        if (isFull()) {
            throw new IllegalStateException("Course " + courseCode + " is at full capacity.");
        }
        if (enrolledStudentIds.contains(studentId)) {
            throw new IllegalStateException("Student already enrolled in " + courseCode);
        }
        enrolledStudentIds.add(studentId);
    }

    public void removeStudent(String studentId) {
        enrolledStudentIds.remove(studentId);
    }

    @Override
    public String toString() {
        String instructorName = (instructor != null) ? instructor.getFullName() : "Unassigned";
        return String.format("%s - %s (%d credits) | Instructor: %s | Enrolled: %d/%d",
                courseCode, courseName, credits, instructorName, enrolledStudentIds.size(), capacity);
    }
}
