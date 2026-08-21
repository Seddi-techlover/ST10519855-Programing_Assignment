package com.mycompany.hospital_management_console;
import java.util.ArrayList;
import java.util.List;

public class Ward_Manager {
    private List<Patient_Management> patients;
    private String[][] bedLayout;
    private static final int ROWS = 4;
    private static final int COLS = 5;
    private static int patientCounter = 1;

    public Ward_Manager() {
        this.patients = new ArrayList<>();
        this.bedLayout = new String[][]{
            {"B01", "B02", "B03", "B04", "B05"},
            {"B06", "B07", "B08", "B09", "B10"},
            {"B11", "B12", "B13", "B14", "B15"},
            {"B16", "B17", "B18", "B19", "B20"}
        };
    }

    // --- PATIENT MANAGEMENT ---

    public boolean addPatient(Patient_Management patient) {
        if (findPatientById(patient.getPatientID()) != null) {
            return false; // Prevent duplicate IDs
        }
        return patients.add(patient);
    }

    public Patient_Management findPatientById(String id) {
        for (Patient_Management p : patients) {
            if (p.getPatientID().equalsIgnoreCase(id)) {
                return p;
            }
        }
        return null;
    }

    // --- BED MANAGEMENT ---

    // Check if a specific bed code exists and is currently available
    public boolean isBedAvailable(String bedCode) {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (bedLayout[r][c].equalsIgnoreCase(bedCode)) {
                    return true;
                }
            }
        }
        return false; // Bed either occupied or invalid code
    }

    // Allocate an available bed to an Inpatient
    public boolean allocateBed(String patientId, String bedCode) {
        Patient_Management patient = findPatientById(patientId);

        // Rule 1: Patient must exist and be an INPATIENT
        if (patient == null || patient.getcategory() != Patient_Category.INPATIENT) {
            return false;
        }

        // Rule 2: Bed must be available
        if (!isBedAvailable(bedCode)) {
            return false;
        }

        // Allocate by replacing the bed code with "OCCUPIED (" + patientId + ")"
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (bedLayout[r][c].equalsIgnoreCase(bedCode)) {
                    bedLayout[r][c] = "OCCUPIED"; 
                    return true;
                }
            }
        }
        return false;
    }

    // Release a bed when a patient is discharged
    public boolean releaseBed(String bedCode) {
        // Simple release: reset the position back to its original bed ID
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                // If it was occupied and matches the intended slot
                if (bedLayout[r][c].equals("OCCUPIED")) {
                    // Reset to standard format like B01, B02 based on index
                    int bedNum = (r * COLS) + c + 1;
                    String defaultCode = String.format("B%02d", bedNum);
                    
                    if (defaultCode.equalsIgnoreCase(bedCode)) {
                        bedLayout[r][c] = defaultCode;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // --- DISPLAY & REPORT METHODS ---

    public void displayWardLayout() {
        System.out.println("\n--- Current Ward Bed Layout ---");
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                System.out.print(bedLayout[r][c] + "\t");
            }
            System.out.println();
        }
    }

    public int getOccupiedBedCount() {
        int count = 0;
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (bedLayout[r][c].equals("OCCUPIED")) {
                    count++;
                }
            }
        }
        return count;
    }

    public double getOccupancyPercentage() {
        double totalBeds = ROWS * COLS;
        return (getOccupiedBedCount() / totalBeds) * 100.0;
    }
    
    public boolean deletePatient(String patientID) {
    Patient_Management patient = findPatientById(patientID);
    
    if (patient == null) {
        return false; // Patient not found
    }

    // 1. If patient is in a bed, release the bed first
    for (int r = 0; r < ROWS; r++) {
        for (int c = 0; c < COLS; c++) {
            if (bedLayout[r][c].contains(patientID)) {
                // Reset bed back to available code (e.g., "B01")
                bedLayout[r][c] = "B" + String.format("%02d", (r * COLS) + c + 1);
            }
        }
    }

    // 2. Remove patient from the list
    return patients.remove(patient);
   } 
    
    // Helper method to auto-generate formatted ID (e.g., "P001")
    public String generatePatientID() {
        return String.format("P%03d", patientCounter++);
    }

    // Displays full details for a specific patient
    public void printPatientReport(String patientID) {
        Patient_Management patient = findPatientById(patientID);
        if (patient == null) {
            System.out.println("Error: Patient with ID " + patientID + " not found.");
            return;
        }

        System.out.println("\n----------------------------------");
        System.out.println("      PATIENT MEDICAL REPORT      ");
        System.out.println("----------------------------------");
        System.out.println("Patient ID       : " + patient.getPatientID());
        System.out.println("Full Name        : " + patient.getFirstName() + " " + patient.getLastName());
        System.out.println("Age              : " + patient.getAge());
        System.out.println("Gender           : " + patient.getGender());
        System.out.println("Medical Condition: " + patient.getMedicalCondition());
        System.out.println("Category         : " + patient.getcategory());
        System.out.println("----------------------------------");
    }

  

}

