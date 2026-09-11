package com.university.sms;

/** Hourly-paid instructor whose pay depends on hours taught per week. */
public class PartTimeInstructor extends Instructor {

    private double hourlyRate;
    private int hoursPerWeek;

    public PartTimeInstructor(String id, String firstName, String lastName, String email,
                               String department, double hourlyRate, int hoursPerWeek) {
        super(id, firstName, lastName, email, department);
        this.hourlyRate = hourlyRate;
        this.hoursPerWeek = hoursPerWeek;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public int getHoursPerWeek() {
        return hoursPerWeek;
    }

    public void setHoursPerWeek(int hoursPerWeek) {
        this.hoursPerWeek = hoursPerWeek;
    }

    @Override
    public double calculateSalary() {
        return hourlyRate * hoursPerWeek * 4.0; // approx. monthly pay (4 weeks)
    }

    @Override
    public String getRole() {
        return "Part-Time Instructor";
    }
}
