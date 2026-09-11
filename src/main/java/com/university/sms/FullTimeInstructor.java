package com.university.sms;

/** Salaried instructor with a fixed annual salary. */
public class FullTimeInstructor extends Instructor {

    private double annualSalary;

    public FullTimeInstructor(String id, String firstName, String lastName, String email,
                               String department, double annualSalary) {
        super(id, firstName, lastName, email, department);
        this.annualSalary = annualSalary;
    }

    public double getAnnualSalary() {
        return annualSalary;
    }

    public void setAnnualSalary(double annualSalary) {
        if (annualSalary < 0) throw new IllegalArgumentException("Salary cannot be negative.");
        this.annualSalary = annualSalary;
    }

    @Override
    public double calculateSalary() {
        return annualSalary / 12.0; // monthly pay
    }

    @Override
    public String getRole() {
        return "Full-Time Instructor";
    }
}
