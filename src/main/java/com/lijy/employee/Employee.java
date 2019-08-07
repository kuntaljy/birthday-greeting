package com.lijy.employee;

import java.time.LocalDate;

/**
 * @author lijy
 */
public class Employee {

    @Column("first_name")
    private String firstName;
    @Column("last_name")
    private String lastName;
    @Column(value = "date_of_birth", format = "yyyy/MM/dd")
    private LocalDate birthDay;
    @Column("email")
    private String email;

    public boolean isMyBirthDayToday() {
        LocalDate today = LocalDate.now();
        return birthDay.getMonthValue() == today.getMonthValue()
                && birthDay.getDayOfMonth() == today.getDayOfMonth();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
