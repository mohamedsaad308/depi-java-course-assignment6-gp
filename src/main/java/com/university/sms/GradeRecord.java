package com.university.sms;

/**
 * Represents a single grade earned by a student in a course.
 * Encapsulates the numeric score and derives the letter grade
 * and grade-point value from it, so the conversion logic lives
 * in exactly one place.
 */
public class GradeRecord {

    private final String studentId;
    private final String courseCode;
    private double numericScore; // 0-100

    public GradeRecord(String studentId, String courseCode, double numericScore) {
        this.studentId = studentId;
        this.courseCode = courseCode;
        setNumericScore(numericScore);
    }

    public String getStudentId() {
        return studentId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public double getNumericScore() {
        return numericScore;
    }

    public void setNumericScore(double numericScore) {
        if (numericScore < 0 || numericScore > 100) {
            throw new IllegalArgumentException("Score must be between 0 and 100.");
        }
        this.numericScore = numericScore;
    }

    /** Converts the numeric score into a letter grade. */
    public String getLetterGrade() {
        if (numericScore >= 90) return "A";
        if (numericScore >= 80) return "B";
        if (numericScore >= 70) return "C";
        if (numericScore >= 60) return "D";
        return "F";
    }

    /** Converts the numeric score into standard 4.0-scale grade points. */
    public double getGradePoints() {
        return switch (getLetterGrade()) {
            case "A" -> 4.0;
            case "B" -> 3.0;
            case "C" -> 2.0;
            case "D" -> 1.0;
            default -> 0.0;
        };
    }

    @Override
    public String toString() {
        return String.format("%s: %.1f (%s, %.1f pts)", courseCode, numericScore, getLetterGrade(), getGradePoints());
    }
}
