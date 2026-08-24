package com.mycompany.hospital_management_console;

/**
 * Manual test class — exercises Ward_Manager's public methods
 * by hand and prints PASS/FAIL for each check.
 * Not JUnit — just a runnable class with a main() method.
 */
public class ManualTest {

    private static int passCount = 0;
    private static int failCount = 0;

    public static void main(String[] args) {

        Ward_Manager ward = new Ward_Manager();

        // --- Test 1: Add a new patient ---
        Patient_Management p1 = new Patient_Management(
                "P001", 45, "Thabo", "Mokoena", "Flu",
                Patient_Category.INPATIENT, "Male");
        check("Add new patient (should succeed)",
                ward.addPatient(p1), true);

        // --- Test 2: Reject duplicate patient ID ---
        Patient_Management p1Duplicate = new Patient_Management(
                "P001", 30, "Jane", "Doe", "Cold",
                Patient_Category.OUTATIENT, "Female");
        check("Add duplicate ID (should fail)",
                ward.addPatient(p1Duplicate), false);

        // --- Test 3: Find existing patient ---
        Patient_Management found = ward.findPatientById("P001");
        check("Find existing patient by ID",
                found != null && found.getFirstName().equals("Thabo"), true);

        // --- Test 4: Find non-existent patient ---
        check("Find non-existent patient (should be null)",
                ward.findPatientById("P999") == null, true);

        // --- Test 5: Check a valid bed code is available ---
        check("Bed B01 available before allocation",
                ward.isBedAvailable("B01"), true);

        // --- Test 6: Check an invalid bed code ---
        check("Invalid bed code B99 (should be false)",
                ward.isBedAvailable("B99"), false);

        // --- Test 7: Allocate bed to an INPATIENT (should succeed) ---
        check("Allocate B01 to INPATIENT P001",
                ward.allocateBed("P001", "B01"), true);

        // --- Test 8: Bed no longer available after allocation ---
        check("B01 unavailable after allocation",
                ward.isBedAvailable("B01"), false);

        // --- Test 9: Try to allocate an already-occupied bed ---
        Patient_Management p2 = new Patient_Management(
                "P002", 60, "Sipho", "Ndlovu", "Diabetes",
                Patient_Category.INPATIENT, "Male");
        ward.addPatient(p2);
        check("Allocate already-occupied B01 (should fail)",
                ward.allocateBed("P002", "B01"), false);

        // --- Test 10: Try to allocate a bed to a non-INPATIENT ---
        Patient_Management p3 = new Patient_Management(
                "P003", 28, "Ayesha", "Khan", "Checkup",
                Patient_Category.OUTATIENT, "Female");
        ward.addPatient(p3);
        check("Allocate bed to OUTPATIENT (should fail)",
                ward.allocateBed("P003", "B02"), false);

        // --- Test 11: Occupied bed count after one allocation ---
        check("Occupied bed count should be 1",
                ward.getOccupiedBedCount(), 1);

        // --- Test 12: Occupancy percentage (1 of 20 beds = 5.0%) ---
        check("Occupancy percentage should be 5.0",
                ward.getOccupancyPercentage(), 5.0);

        // --- Test 13: Release the occupied bed ---
        check("Release B01 (should succeed)",
                ward.releaseBed("B01"), true);

        // --- Test 14: Occupied count back to 0 after release ---
        check("Occupied bed count back to 0 after release",
                ward.getOccupiedBedCount(), 0);

        // --- Visual check: print the ward layout ---
        ward.displayWardLayout();

        // --- Summary ---
        System.out.println("\n--- TEST SUMMARY ---");
        System.out.println("Passed: " + passCount);
        System.out.println("Failed: " + failCount);
    }

    // Overload for boolean checks
    private static void check(String testName, boolean actual, boolean expected) {
        boolean result = actual == expected;
        report(testName, result, String.valueOf(expected), String.valueOf(actual));
    }

    // Overload for int checks
    private static void check(String testName, int actual, int expected) {
        boolean result = actual == expected;
        report(testName, result, String.valueOf(expected), String.valueOf(actual));
    }

    // Overload for double checks
    private static void check(String testName, double actual, double expected) {
        boolean result = Math.abs(actual - expected) < 0.001;
        report(testName, result, String.valueOf(expected), String.valueOf(actual));
    }

    private static void report(String testName, boolean passed, String expected, String actual) {
        if (passed) {
            passCount++;
            System.out.println("[PASS] " + testName);
        } else {
            failCount++;
            System.out.println("[FAIL] " + testName + " | expected: " + expected + " | actual: " + actual);
        }
    }
}
