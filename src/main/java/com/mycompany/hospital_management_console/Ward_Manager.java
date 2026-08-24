package com.mycompany.hospital_management_console;
import java.util.ArrayList;
import java.util.List;

public class Ward_Manager {
    private List<Patient_Management> patients;
    private String[][] bedLayout;
    private static final int ROWS = 4;
    private static final int COLS = 5;

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

    public boolean deletePatient(String id) {
        Patient_Management patient = findPatientById(id);
        if (patient == null) {
            return false;
        }
        releaseBedByPatientId(id); // free up their bed, if any, before removing
        return patients.remove(patient);
    }

    public void displayAllPatients() {
        System.out.println("\n--- ALL REGISTERED PATIENTS ---");
        if (patients.isEmpty()) {
            System.out.println("No patients registered.");
            return;
        }
        for (Patient_Management p : patients) {
            System.out.println(p.getDetails());
        }
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
        if (patient == null || patient.getCategory() != Patient_Category.INPATIENT) {
            System.out.println("ERROR: Bed allocation denied. Patient must be registered as INPATIENT.");
            return false;
        }

        // Rule 2: Bed must exist and be available
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (bedLayout[r][c].equalsIgnoreCase(bedCode)) {
                    bedLayout[r][c] = "OCCUPIED:" + patientId;
                    return true;
                }
            }
        }
        System.out.println("ERROR: Bed code not found or already occupied.");
        return false;
    }

    // Release a bed when a patient is discharged, using its own code (e.g. "B01")
    public boolean releaseBed(String bedCode) {
        int count = 1;
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                String defaultCode = String.format("B%02d", count);
                if (defaultCode.equalsIgnoreCase(bedCode) && bedLayout[r][c].startsWith("OCCUPIED")) {
                    bedLayout[r][c] = defaultCode;
                    return true;
                }
                count++;
            }
        }
        return false;
    }

    // Release whatever bed a given patient currently occupies (used by deletePatient)
    private void releaseBedByPatientId(String patientId) {
        int count = 1;
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                String defaultCode = String.format("B%02d", count);
                if (bedLayout[r][c].equalsIgnoreCase("OCCUPIED:" + patientId)) {
                    bedLayout[r][c] = defaultCode;
                }
                count++;
            }
        }
    }

    // --- DISPLAY & REPORT METHODS ---

    // Displays full details for a specific patient
    public void printPatientReport(String patientID) {
        Patient_Management patient = findPatientById(patientID);
        if (patient == null) {
            System.out.println("Error: Patient with ID " + patientID + " not found.");
            return;
        }

        System.out.println("\n-----------------------------------");
        System.out.println("        PATIENT MEDICAL REPORT      ");
        System.out.println("-----------------------------------");
        System.out.println("Patient ID       : " + patient.getPatientID());
        System.out.println("Full Name        : " + patient.getFirstName() + " " + patient.getLastName());
        System.out.println("Age              : " + patient.getAge());
        System.out.println("Gender           : " + patient.getGender());
        System.out.println("Medical Condition: " + patient.getMedicalCondition());
        System.out.println("Category         : " + patient.getCategory());
        System.out.println("-----------------------------------");
    }

    public void displayWardLayout() {
        System.out.println("\n--- CURRENT WARD BED LAYOUT ---");
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                System.out.print("[" + bedLayout[r][c] + "]\t");
            }
            System.out.println();
        }
    }

    public int getOccupiedBedCount() {
        int count = 0;
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (bedLayout[r][c].startsWith("OCCUPIED")) {
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

    public void generateWardReport() {
        int totalBeds = ROWS * COLS;
        int occupied = getOccupiedBedCount();
        int available = totalBeds - occupied;

        System.out.println("\n=====================================");
        System.out.println("          WARD SUMMARY REPORT");
        System.out.println("=====================================");
        System.out.println("Total Registered Patients : " + patients.size());
        System.out.println("Total Beds                : " + totalBeds);
        System.out.println("Occupied Beds              : " + occupied);
        System.out.println("Available Beds              : " + available);
        System.out.printf("Occupancy Rate              : %.2f%%%n", getOccupancyPercentage());
        System.out.println("=====================================");
    }
}