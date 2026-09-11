package com.university.sms;

/**
 * Demonstration entry point. Wires together students, instructors,
 * and courses, then exercises the CRUD, enrollment, grading, and
 * reporting features of StudentManagementSystem.
 */
public class Main {
    public static void main(String[] args) {
        StudentManagementSystem sms = new StudentManagementSystem();

        // --- Create instructors (polymorphism: two Instructor subtypes) ---
        Instructor drSmith = new FullTimeInstructor("I001", "Alice", "Smith",
                "alice.smith@university.edu", "Computer Science", 84000.0);
        Instructor mrLee = new PartTimeInstructor("I002", "Ben", "Lee",
                "ben.lee@university.edu", "Mathematics", 45.0, 10);
        sms.addInstructor(drSmith);
        sms.addInstructor(mrLee);

        // --- Create courses ---
        Course cs101 = new Course("CS101", "Introduction to Programming", 3, 2);
        Course math201 = new Course("MATH201", "Linear Algebra", 4, 30);
        sms.addCourse(cs101);
        sms.addCourse(math201);
        sms.assignInstructorToCourse("I001", "CS101");
        sms.assignInstructorToCourse("I002", "MATH201");

        // --- Create students (polymorphism: two Student subtypes) ---
        Student john = new UndergraduateStudent("S001", "John", "Doe",
                "john.doe@university.edu", 1, 15);
        Student maria = new GraduateStudent("S002", "Maria", "Garcia",
                "maria.garcia@university.edu", "Machine Learning", "I001", 9);
        sms.addStudent(john);
        sms.addStudent(maria);

        // --- Enrollment ---
        sms.enrollStudentInCourse("S001", "CS101");
        sms.enrollStudentInCourse("S001", "MATH201");
        sms.enrollStudentInCourse("S002", "CS101");

        // --- Attendance ---
        sms.markAttendance("S001", "CS101", true);
        sms.markAttendance("S001", "CS101", true);
        sms.markAttendance("S001", "CS101", false);
        sms.markAttendance("S002", "CS101", true);

        // --- Grades ---
        sms.recordGrade("S001", "CS101", 92);
        sms.recordGrade("S001", "MATH201", 78);
        sms.recordGrade("S002", "CS101", 88);

        // --- Reports (demonstrates polymorphic getRole()/calculateTuition() calls) ---
        System.out.println("=== All People ===");
        for (Student s : sms.listStudents()) {
            System.out.println(s);
        }
        for (Instructor i : sms.listInstructors()) {
            System.out.println(i + String.format(" | Monthly pay: $%.2f", i.calculateSalary()));
        }

        System.out.println("\n=== Courses ===");
        for (Course c : sms.listCourses()) {
            System.out.println(c);
        }

        System.out.println("\n=== Transcripts ===");
        System.out.println(sms.generateTranscript("S001"));
        System.out.println(sms.generateTranscript("S002"));

        // --- Edit and delete demonstration ---
        sms.editStudentContactInfo("S001", null, null, "john.doe2@university.edu");
        System.out.println("Updated email: " + sms.getStudent("S001").getEmail());

        sms.deleteStudent("S002");
        System.out.println("\nAfter deleting S002, CS101 status: " + sms.getCourse("CS101"));
    }
}
