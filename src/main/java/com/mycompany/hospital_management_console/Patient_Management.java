package com.mycompany.hospital_management_console;
//Parent class
/**
 *
 * @author rptsu
 */
public class Patient_Management {
    private String patientID;
    private int age;
    private String firstName, lastName;
    private String medicalCondition;
    private Patient_Category category;
    private String gender;

    public Patient_Management(String patientID, int age, String firstName, String lastName,
            String medicalCondition, Patient_Category category, String gender) {
        this.patientID = patientID;
        this.age = age;
        this.firstName = firstName;
        this.lastName = lastName;
        this.medicalCondition = medicalCondition;
        this.category = category;
        this.gender = gender;
    }

    // --- GETTERS ---

    public String getPatientID() {
        return patientID;
    }

    public int getAge() {
        return age;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMedicalCondition() {
        return medicalCondition;
    }

    public Patient_Category getCategory() {
        return category;
    }

    public String getGender() {
        return gender;
    }

    // --- SETTERS (needed for "Update Existing Patient Details") ---
    // No setPatientID() — an ID should not change once assigned.

    public void setAge(int age) {
        this.age = age;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMedicalCondition(String medicalCondition) {
        this.medicalCondition = medicalCondition;
    }

    public void setCategory(Patient_Category category) {
        this.category = category;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    // --- DISPLAY ---

    public String getDetails() {
        return "ID:" + patientID + " | Name: " + firstName + " " + lastName
                + " | Age:" + age + " | Gender:" + gender
                + " | Condition:" + medicalCondition
                + " | Category:" + category;
    }
}
    
    
    
