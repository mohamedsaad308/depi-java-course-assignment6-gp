package com.university.sms;

/**
 * Concrete Student subclass for graduate students.
 * Tuition uses a higher flat per-credit rate; standing tracks
 * thesis/research progress instead of year level.
 */
public class GraduateStudent extends Student {

    private static final double RATE_PER_CREDIT = 600.0;
    private String researchArea;
    private String advisorId;
    private int creditsThisTerm;
    private boolean thesisSubmitted;

    public GraduateStudent(String id, String firstName, String lastName, String email,
                            String researchArea, String advisorId, int creditsThisTerm) {
        super(id, firstName, lastName, email);
        this.researchArea = researchArea;
        this.advisorId = advisorId;
        this.creditsThisTerm = creditsThisTerm;
        this.thesisSubmitted = false;
    }

    public String getResearchArea() {
        return researchArea;
    }

    public void setResearchArea(String researchArea) {
        this.researchArea = researchArea;
    }

    public String getAdvisorId() {
        return advisorId;
    }

    public void setAdvisorId(String advisorId) {
        this.advisorId = advisorId;
    }

    public void submitThesis() {
        this.thesisSubmitted = true;
    }

    @Override
    public double calculateTuition() {
        return creditsThisTerm * RATE_PER_CREDIT;
    }

    @Override
    public String getAcademicStanding() {
        if (thesisSubmitted) return "Thesis Submitted - Pending Defense";
        double gpa = calculateGPA();
        if (gpa < 2.5) return "Academic Warning (Graduate)";
        return "Active Candidate";
    }

    @Override
    public String getRole() {
        return "Graduate Student";
    }
}
