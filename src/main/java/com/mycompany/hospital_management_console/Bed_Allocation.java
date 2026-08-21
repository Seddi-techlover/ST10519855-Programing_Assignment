package com.mycompany.hospital_management_console;
//subClass
/**
 *
 * @author rptsu
 */
public class Bed_Allocation extends Patient_Management {
    private int wardNumber;
    private String bedNumber;

    public Bed_Allocation(String patientID, int age, String firstName, String lastName, String medicalCondition, Patient_Category category, String gender,int wardNumber,String bedNumber) {
        super(patientID, age, firstName, lastName, medicalCondition, category, gender);
        this.bedNumber = bedNumber;
        this.wardNumber = wardNumber;
    }
   
    
  
    }
    

