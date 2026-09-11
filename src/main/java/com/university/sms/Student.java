package com.university.sms;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract Student class. Concrete subclasses (UndergraduateStudent,
 * GraduateStudent) specialise tuition calculation and academic
 * standing, demonstrating inheritance and polymorphism.
 */
public abstract class Student extends Person {

    private final List<String> enrolledCourseCodes = new ArrayList<>();
    private final Map<String, GradeRecord> grades = new LinkedHashMap<>(); // courseCode -> grade
    private final Map<String, List<Boolean>> attendance = new LinkedHashMap<>(); // courseCode -> present/absent log

    public Student(String id, String firstName, String lastName, String email) {
        super(id, firstName, lastName, email);
    }

    public List<String> getEnrolledCourseCodes() {
        return List.copyOf(enrolledCourseCodes);
    }

    public void addCourse(String courseCode) {
        if (!enrolledCourseCodes.contains(courseCode)) {
            enrolledCourseCodes.add(courseCode);
        }
    }

    public void removeCourse(String courseCode) {
        enrolledCourseCodes.remove(courseCode);
        grades.remove(courseCode);
        attendance.remove(courseCode);
    }

    public void recordGrade(GradeRecord grade) {
        if (!enrolledCourseCodes.contains(grade.getCourseCode())) {
            throw new IllegalStateException("Student is not enrolled in " + grade.getCourseCode());
        }
        grades.put(grade.getCourseCode(), grade);
    }

    public Map<String, GradeRecord> getGrades() {
        return Map.copyOf(grades);
    }

    /** Calculates a simple unweighted GPA across all recorded grades. */
    public double calculateGPA() {
        if (grades.isEmpty()) return 0.0;
        double total = 0;
        for (GradeRecord g : grades.values()) {
            total += g.getGradePoints();
        }
        return total / grades.size();
    }

    public void markAttendance(String courseCode, boolean present) {
        attendance.computeIfAbsent(courseCode, k -> new ArrayList<>()).add(present);
    }

    /** Returns attendance rate for a course as a percentage (0-100). */
    public double getAttendanceRate(String courseCode) {
        List<Boolean> log = attendance.get(courseCode);
        if (log == null || log.isEmpty()) return 0.0;
        long presentCount = log.stream().filter(b -> b).count();
        return (presentCount * 100.0) / log.size();
    }

    /** Subclasses define how tuition is calculated (per-credit vs flat rate, etc.). */
    public abstract double calculateTuition();

    /** Subclasses define academic-standing rules specific to their level. */
    public abstract String getAcademicStanding();

    @Override
    public String getRole() {
        return "Student";
    }
}
