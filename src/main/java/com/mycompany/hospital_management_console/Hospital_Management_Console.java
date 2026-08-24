package com.mycompany.hospital_management_console;
import java.util.Scanner;
/**
 *
 * @author rptsu
 */
public class Hospital_Management_Console {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Ward_Manager ward = new Ward_Manager();
        boolean running = true;

        while (running) {
            System.out.println("\n-------------------------------------");
            System.out.println("HOSPITAL RECORDS MANAGEMENT");
            System.out.println("-------------------------------------");
            System.out.println("===Select Your Option Below===");
            System.out.println("1) Register New Patient");
            System.out.println("2) Search For Patient (Using existing ID)");
            System.out.println("3) Update Existing Patient Details");
            System.out.println("4) Delete A Patient");
            System.out.println("5) Display All Registered Patients");
            System.out.println("6) Allocate Bed To Patient");
            System.out.println("7) Release A Bed");
            System.out.println("8) Display Ward Bed Layout");
            System.out.println("9) Generate Ward Report");
            System.out.println("10) Exit");
            System.out.print("Enter choice: ");

            int option = scan.nextInt();
            scan.nextLine(); // clear leftover newline before reading strings

            switch (option) {
                case 1: {
                    System.out.println("--Please Enter Patient Information--");

                    System.out.print("Patient ID: ");
                    String id = scan.nextLine();

                    System.out.print("First Name: ");
                    String firstName = scan.nextLine();

                    System.out.print("Last Name: ");
                    String lastName = scan.nextLine();

                    System.out.print("Age: ");
                    int age = scan.nextInt();
                    scan.nextLine();

                    System.out.print("Gender: ");
                    String gender = scan.nextLine();

                    System.out.print("Medical Condition: ");
                    String condition = scan.nextLine();

                    System.out.print("Category (1=INPATIENT, 2=OUTPATIENT, 3=EMERGENCY): ");
                    int catChoice = scan.nextInt();
                    scan.nextLine();
                    Patient_Category category = switch (catChoice) {
                        case 1 -> Patient_Category.INPATIENT;
                        case 3 -> Patient_Category.EMERGENCY;
                        default -> Patient_Category.OUTATIENT;
                    };

                    Patient_Management newPatient = new Patient_Management(
                            id, age, firstName, lastName, condition, category, gender);

                    if (ward.addPatient(newPatient)) {
                        System.out.println("SUCCESS: Patient registered.");
                    } else {
                        System.out.println("ERROR: A patient with that ID already exists.");
                    }
                    break;
                }
                case 2: {
                    System.out.print("Enter Patient ID to search: ");
                    String id = scan.nextLine();
                    Patient_Management found = ward.findPatientById(id);
                    if (found != null) {
                        System.out.println(found.getDetails());
                    } else {
                        System.out.println("ERROR: Patient not found.");
                    }
                    break;
                }
                case 3: {
                    System.out.print("Enter Patient ID to update: ");
                    String id = scan.nextLine();
                    Patient_Management found = ward.findPatientById(id);
                    if (found == null) {
                        System.out.println("ERROR: Patient not found.");
                        break;
                    }

                    System.out.println("Leave a field blank to keep its current value.");

                    System.out.print("New First Name [" + found.getFirstName() + "]: ");
                    String firstName = scan.nextLine();
                    if (!firstName.isBlank()) found.setFirstName(firstName);

                    System.out.print("New Last Name [" + found.getLastName() + "]: ");
                    String lastName = scan.nextLine();
                    if (!lastName.isBlank()) found.setLastName(lastName);

                    System.out.print("New Age [" + found.getAge() + "] (enter -1 to keep): ");
                    int age = scan.nextInt();
                    scan.nextLine();
                    if (age != -1) found.setAge(age);

                    System.out.print("New Gender [" + found.getGender() + "]: ");
                    String gender = scan.nextLine();
                    if (!gender.isBlank()) found.setGender(gender);

                    System.out.print("New Medical Condition [" + found.getMedicalCondition() + "]: ");
                    String condition = scan.nextLine();
                    if (!condition.isBlank()) found.setMedicalCondition(condition);

                    System.out.println("SUCCESS: Patient details updated.");
                    break;
                }
                case 4: {
                    System.out.print("Enter Patient ID to delete: ");
                    String id = scan.nextLine();
                    if (ward.deletePatient(id)) {
                        System.out.println("SUCCESS: Patient deleted.");
                    } else {
                        System.out.println("ERROR: Patient not found.");
                    }
                    break;
                }
                case 5:
                    ward.displayAllPatients();
                    break;
                case 6: {
                    System.out.print("Enter Patient ID to allocate a bed to: ");
                    String id = scan.nextLine();
                    System.out.print("Enter Bed Code (e.g. B01): ");
                    String bedCode = scan.nextLine();
                    if (ward.allocateBed(id, bedCode)) {
                        System.out.println("SUCCESS: Bed allocated.");
                    }
                    break;
                }
                case 7: {
                    System.out.print("Enter Bed Code to release (e.g. B01): ");
                    String bedCode = scan.nextLine();
                    if (ward.releaseBed(bedCode)) {
                        System.out.println("SUCCESS: Bed released.");
                    } else {
                        System.out.println("ERROR: Could not release that bed.");
                    }
                    break;
                }
                case 8:
                    ward.displayWardLayout();
                    break;
                case 9:
                    ward.generateWardReport();
                    break;
                case 10:
                    running = false;
                    System.out.println("Exiting System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid Menu Choice");
            }
        }
        scan.close();
    }
}