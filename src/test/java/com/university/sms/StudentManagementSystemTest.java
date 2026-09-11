package com.university.sms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentManagementSystemTest {

    private StudentManagementSystem sms;

    @BeforeEach
    void setUp() {
        sms = new StudentManagementSystem();
        sms.addStudent(new UndergraduateStudent("S001", "John", "Doe",
                "john@test.edu", 1, 15));
        sms.addInstructor(new FullTimeInstructor("I001", "Alice", "Smith",
                "alice@test.edu", "CS", 84000.0));
        sms.addCourse(new Course("CS101", "Intro to Programming", 3, 1));
        sms.assignInstructorToCourse("I001", "CS101");
    }

    @Test
    void gradeRecordConvertsScoreToLetterGrade() {
        GradeRecord grade = new GradeRecord("S001", "CS101", 92);
        assertEquals("A", grade.getLetterGrade());
        assertEquals(4.0, grade.getGradePoints());
    }

    @Test
    void enrollmentRespectsCourseCapacity() {
        sms.enrollStudentInCourse("S001", "CS101");
        sms.addStudent(new UndergraduateStudent("S002", "Jane", "Roe",
                "jane@test.edu", 1, 15));
        assertThrows(IllegalStateException.class,
                () -> sms.enrollStudentInCourse("S002", "CS101"));
    }

    @Test
    void gpaIsAveragedAcrossRecordedGrades() {
        sms.enrollStudentInCourse("S001", "CS101");
        sms.recordGrade("S001", "CS101", 92); // A = 4.0
        Student s = sms.getStudent("S001");
        assertEquals(4.0, s.calculateGPA(), 0.001);
    }

    @Test
    void undergraduateTuitionIsCreditsTimesRate() {
        Student s = sms.getStudent("S001");
        // 15 credits * $350/credit
        assertEquals(5250.0, s.calculateTuition(), 0.001);
    }

    @Test
    void deletingStudentRemovesThemFromCourseRoster() {
        sms.enrollStudentInCourse("S001", "CS101");
        sms.deleteStudent("S001");
        assertFalse(sms.getCourse("CS101").getEnrolledStudentIds().contains("S001"));
    }
}
