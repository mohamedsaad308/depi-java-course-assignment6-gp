package com.university.sms;

/**
 * Concrete Student subclass for undergraduates.
 * Tuition is charged per credit hour; standing is based on year level.
 */
public class UndergraduateStudent extends Student {

    private static final double RATE_PER_CREDIT = 350.0;
    private int yearLevel; // 1 = Freshman ... 4 = Senior
    private int creditsThisTerm;

    public UndergraduateStudent(String id, String firstName, String lastName, String email,
                                 int yearLevel, int creditsThisTerm) {
        super(id, firstName, lastName, email);
        setYearLevel(yearLevel);
        this.creditsThisTerm = creditsThisTerm;
    }

    public int getYearLevel() {
        return yearLevel;
    }

    public void setYearLevel(int yearLevel) {
        if (yearLevel < 1 || yearLevel > 4) {
            throw new IllegalArgumentException("Year level must be between 1 and 4.");
        }
        this.yearLevel = yearLevel;
    }

    public int getCreditsThisTerm() {
        return creditsThisTerm;
    }

    public void setCreditsThisTerm(int creditsThisTerm) {
        this.creditsThisTerm = creditsThisTerm;
    }

    @Override
    public double calculateTuition() {
        return creditsThisTerm * RATE_PER_CREDIT;
    }

    @Override
    public String getAcademicStanding() {
        double gpa = calculateGPA();
        if (gpa < 1.5) return "Academic Probation";
        return switch (yearLevel) {
            case 1 -> "Freshman";
            case 2 -> "Sophomore";
            case 3 -> "Junior";
            default -> "Senior";
        };
    }

    @Override
    public String getRole() {
        return "Undergraduate Student";
    }
}
