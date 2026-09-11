# Student Management System (Java)

A console-based system for administrators to manage students, courses,
instructors, grades, and attendance.

## 1. Requirements

- Manage student records (add / edit / delete).
- Manage course records (add / edit / delete), including instructor
  assignment and enrollment capacity.
- Record and calculate grades (numeric score → letter grade → GPA).
- Track attendance per student per course.
- Model different kinds of students and instructors with different
  behavior (tuition, pay, academic standing).

## 2. Design

### Class hierarchy

```
Person (abstract)
 ├── Student (abstract)
 │    ├── UndergraduateStudent
 │    └── GraduateStudent
 └── Instructor (abstract)
      ├── FullTimeInstructor
      └── PartTimeInstructor

Course            (holds roster + instructor reference)
GradeRecord       (numeric score → letter grade / GPA points)
StudentManagementSystem   (admin-facing manager: CRUD + enrollment + grading)
Main              (demo driver)
```

### OOP principles applied

- **Encapsulation** — every class keeps fields `private`, exposes
  validated getters/setters, and returns defensive copies of internal
  lists (e.g. `Course.getEnrolledStudentIds()`, `Student.getGrades()`)
  so callers can't mutate internal state directly.
- **Inheritance** — `Student` and `Instructor` both extend the shared
  `Person` base class; `UndergraduateStudent`/`GraduateStudent` extend
  `Student`; `FullTimeInstructor`/`PartTimeInstructor` extend
  `Instructor`.
- **Polymorphism** — `getRole()`, `calculateTuition()`,
  `getAcademicStanding()`, and `calculateSalary()` are declared
  `abstract` in the base classes and overridden differently in each
  subclass. `Main` and `StudentManagementSystem.generateTranscript()`
  call these through the base-class reference, so behavior varies by
  actual runtime type without any `instanceof` checks.
- **Abstraction** — `Student` and `Instructor` are `abstract` classes
  and can never be instantiated directly; they only describe the
  shared contract that concrete subclasses must implement.

### Key classes

| Class | Responsibility |
|---|---|
| `Person` | Shared identity fields (id, name, email) and validation. |
| `Student` | Enrollment list, grades, attendance log, GPA calculation. |
| `UndergraduateStudent` | Per-credit tuition; year-level standing (Freshman–Senior). |
| `GraduateStudent` | Higher per-credit tuition; thesis/candidacy standing. |
| `Instructor` | Department, assigned courses. |
| `FullTimeInstructor` | Salary ÷ 12 as monthly pay. |
| `PartTimeInstructor` | Hourly rate × hours/week × 4 as monthly pay. |
| `Course` | Roster, capacity enforcement, instructor assignment. |
| `GradeRecord` | Converts a 0–100 score into a letter grade and GPA points. |
| `StudentManagementSystem` | All CRUD operations; the single entry point an admin tool would use. |

## 3. How to compile and run

This is a standard Maven project (`com.university.sms` package).

```bash
mvn compile          # compile only
mvn test             # run the JUnit 5 tests in src/test/java
mvn package          # compile + test + build target/student-management-system.jar
mvn exec:java         # compile + run Main directly, no jar needed

# or, after `mvn package`:
java -jar target/student-management-system.jar
```

Requires Maven and JDK 17+ (set in `pom.xml`'s `maven.compiler.source`/`target`
if you're on a different version).

## 4. Sample workflow (see `Main.java`)

1. Two instructors are created (one full-time, one part-time).
2. Two courses are created and instructors assigned.
3. Two students are created (one undergraduate, one graduate).
4. Both are enrolled in a shared course; attendance and grades are
   recorded.
5. The system prints a roster, a course list, and a per-student
   transcript (GPA, standing, tuition due) — then demonstrates
   editing a student's email and deleting a student, showing the
   course roster update automatically.

## 5. Possible extensions

- Persist data to a file or database instead of in-memory maps.
- Add a `Course` waitlist when at capacity instead of rejecting
  enrollment outright.
- Add role-based authentication for the "administrator" actor.
- Add a weighted-GPA calculation that accounts for course credit
  hours (currently GPA is an unweighted average of grade points).
