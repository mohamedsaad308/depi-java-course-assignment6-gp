package com.university.sms;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Central administrator-facing manager. Owns all data collections
 * as private maps (encapsulation) and exposes controlled CRUD
 * operations for students, courses, and instructors, plus grade
 * and attendance recording. This is the class an admin UI or CLI
 * would call into.
 */
public class StudentManagementSystem {

    private final Map<String, Student> students = new LinkedHashMap<>();
    private final Map<String, Course> courses = new LinkedHashMap<>();
    private final Map<String, Instructor> instructors = new LinkedHashMap<>();

    // ---------------- Student management ----------------

    public void addStudent(Student student) {
        if (students.containsKey(student.getId())) {
            throw new IllegalArgumentException("Student ID already exists: " + student.getId());
        }
        students.put(student.getId(), student);
    }

    public Student getStudent(String studentId) {
        Student s = students.get(studentId);
        if (s == null) throw new NoSuchElementException("No student with ID " + studentId);
        return s;
    }

    /** Edits basic contact fields for a student; academic data is edited via dedicated methods. */
    public void editStudentContactInfo(String studentId, String firstName, String lastName, String email) {
        Student s = getStudent(studentId);
        if (firstName != null) s.setFirstName(firstName);
        if (lastName != null) s.setLastName(lastName);
        if (email != null) s.setEmail(email);
    }

    public void deleteStudent(String studentId) {
        Student s = students.remove(studentId);
        if (s == null) throw new NoSuchElementException("No student with ID " + studentId);
        // clean up references in courses
        for (String courseCode : s.getEnrolledCourseCodes()) {
            Course c = courses.get(courseCode);
            if (c != null) c.removeStudent(studentId);
        }
    }

    public Collection<Student> listStudents() {
        return students.values();
    }

    // ---------------- Instructor management ----------------

    public void addInstructor(Instructor instructor) {
        if (instructors.containsKey(instructor.getId())) {
            throw new IllegalArgumentException("Instructor ID already exists: " + instructor.getId());
        }
        instructors.put(instructor.getId(), instructor);
    }

    public Instructor getInstructor(String instructorId) {
        Instructor i = instructors.get(instructorId);
        if (i == null) throw new NoSuchElementException("No instructor with ID " + instructorId);
        return i;
    }

    public void deleteInstructor(String instructorId) {
        Instructor i = instructors.remove(instructorId);
        if (i == null) throw new NoSuchElementException("No instructor with ID " + instructorId);
        for (String courseCode : i.getAssignedCourseCodes()) {
            Course c = courses.get(courseCode);
            if (c != null && c.getInstructor() == i) c.assignInstructor(null);
        }
    }

    public Collection<Instructor> listInstructors() {
        return instructors.values();
    }

    // ---------------- Course management ----------------

    public void addCourse(Course course) {
        if (courses.containsKey(course.getCourseCode())) {
            throw new IllegalArgumentException("Course code already exists: " + course.getCourseCode());
        }
        courses.put(course.getCourseCode(), course);
    }

    public Course getCourse(String courseCode) {
        Course c = courses.get(courseCode);
        if (c == null) throw new NoSuchElementException("No course with code " + courseCode);
        return c;
    }

    public void editCourse(String courseCode, String newName, Integer newCredits, Integer newCapacity) {
        Course c = getCourse(courseCode);
        if (newName != null) c.setCourseName(newName);
        if (newCredits != null) c.setCredits(newCredits);
        if (newCapacity != null) c.setCapacity(newCapacity);
    }

    public void deleteCourse(String courseCode) {
        Course c = courses.remove(courseCode);
        if (c == null) throw new NoSuchElementException("No course with code " + courseCode);
        for (String studentId : c.getEnrolledStudentIds()) {
            Student s = students.get(studentId);
            if (s != null) s.removeCourse(courseCode);
        }
        if (c.getInstructor() != null) {
            c.getInstructor().unassignCourse(courseCode);
        }
    }

    public Collection<Course> listCourses() {
        return courses.values();
    }

    public void assignInstructorToCourse(String instructorId, String courseCode) {
        Instructor instructor = getInstructor(instructorId);
        Course course = getCourse(courseCode);
        course.assignInstructor(instructor);
        instructor.assignCourse(courseCode);
    }

    // ---------------- Enrollment, grading, attendance ----------------

    public void enrollStudentInCourse(String studentId, String courseCode) {
        Student student = getStudent(studentId);
        Course course = getCourse(courseCode);
        course.enrollStudent(studentId); // throws if full/duplicate
        student.addCourse(courseCode);
    }

    public void recordGrade(String studentId, String courseCode, double numericScore) {
        Student student = getStudent(studentId);
        student.recordGrade(new GradeRecord(studentId, courseCode, numericScore));
    }

    public void markAttendance(String studentId, String courseCode, boolean present) {
        Student student = getStudent(studentId);
        if (!student.getEnrolledCourseCodes().contains(courseCode)) {
            throw new IllegalStateException("Student is not enrolled in " + courseCode);
        }
        student.markAttendance(courseCode, present);
    }

    /** Prints a simple transcript-style report for one student, demonstrating polymorphic method calls. */
    public String generateTranscript(String studentId) {
        Student s = getStudent(studentId);
        StringBuilder sb = new StringBuilder();
        sb.append("Transcript for ").append(s.getFullName())
          .append(" (").append(s.getRole()).append(")\n");
        for (String courseCode : s.getEnrolledCourseCodes()) {
            GradeRecord g = s.getGrades().get(courseCode);
            String gradeStr = (g != null) ? g.toString() : courseCode + ": (no grade yet)";
            double attendance = s.getAttendanceRate(courseCode);
            sb.append("  - ").append(gradeStr)
              .append(String.format(" | Attendance: %.0f%%", attendance)).append("\n");
        }
        sb.append(String.format("  GPA: %.2f | Standing: %s | Tuition Due: $%.2f\n",
                s.calculateGPA(), s.getAcademicStanding(), s.calculateTuition()));
        return sb.toString();
    }

    /** Simple custom exception-free lookup miss indicator using a standard JDK exception. */
    public static class NoSuchElementException extends RuntimeException {
        public NoSuchElementException(String message) {
            super(message);
        }
    }
}
