package com.mycompany.hospital_management_console;

import java.util.Scanner;

public class Hospital_Management_Console {

    public static void main(String[] args) {
        Ward_Manager manager = new Ward_Manager();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n==================================");
            System.out.println("   MEDICARE HOSPITAL MANAGEMENT   ");
            System.out.println("==================================");
            System.out.println("1. Register New Patient");
            System.out.println("2. View Specific Patient Report");
            System.out.println("3. Delete Patient Record");
            System.out.println("4. Exit");
            System.out.print("Enter choice (1-4): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    // Auto-generate ID
                    String generatedID = manager.generatePatientID();
                    System.out.println("\n--- Registering Patient ID: " + generatedID + " ---");

                    System.out.print("Enter First Name: ");
                    String firstName = scanner.nextLine();

                    System.out.print("Enter Last Name: ");
                    String lastName = scanner.nextLine();

                    System.out.print("Enter Age: ");
                    int age = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter Gender: ");
                    String gender = scanner.nextLine();

                    System.out.print("Enter Medical Condition: ");
                    String medicalCondition = scanner.nextLine();

                    System.out.print("Enter Category (1 for INPATIENT, 2 for OUTPATIENT): ");
                    int catChoice = scanner.nextInt();
                    scanner.nextLine();

                    Patient_Category category = (catChoice == 1) 
                            ? Patient_Category.INPATIENT 
                            : Patient_Category.OUTATIENT;

                    Patient_Management newPatient = new Patient_Management(
                        generatedID, age, firstName, lastName, medicalCondition, category, gender
                    );

                    if (manager.addPatient(newPatient)) {
                        System.out.println("SUCCESS: Patient registered with ID " + generatedID);
                    }
                    break;

                case 2:
                    System.out.print("Enter Patient ID for Report (e.g., P001): ");
                    String reportID = scanner.nextLine();
                    manager.printPatientReport(reportID);
                    break;

                case 3:
                    System.out.print("Enter Patient ID to Delete: ");
                    String delID = scanner.nextLine();
                    if (manager.deletePatient(delID)) {
                        System.out.println("SUCCESS: Patient " + delID + " deleted.");
                    } else {
                        System.out.println("ERROR: Patient ID not found.");
                    }
                    break;

                case 4:
                    running = false;
                    System.out.println("Exiting system. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option. Enter 1-4.");
            }
        }
        scanner.close();
    }
}