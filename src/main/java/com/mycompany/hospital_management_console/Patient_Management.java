package com.mycompany.hospital_management_console; 
//Parent class
/**
 *
 * @author rptsu
 */
public class Patient_Management {
    private String patientID;
    private int    age;
    private String firstName,lastName;
    private String medicalCondition;
    private Patient_Category category;
    private String gender;

    public Patient_Management(String patientID, int age, String firstName, String lastName, String medicalCondition, Patient_Category category, String gender) {
        this.patientID = patientID;
        this.age = age;
        this.firstName = firstName;
        this.lastName = lastName;
        this.medicalCondition = medicalCondition;
        this.category = category;
        this.gender = gender;
    }

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

    public Patient_Category getcategory() {
        return category;
    }

    public String getGender() {
        return gender;
    }
    
 }

    
    
    
