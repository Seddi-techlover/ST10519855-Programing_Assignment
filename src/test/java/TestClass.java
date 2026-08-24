
import com.mycompany.hospital_management_console.Patient_Category;
import com.mycompany.hospital_management_console.Patient_Management;
import com.mycompany.hospital_management_console.Ward_Manager;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
/**
 *
 * @author rptsu
 */
public class TestClass {
    
    public class Hospital_Test {
    private Ward_Manager manager;
    
    
    
    public Hospital_Test() {
        
    }
    
    @BeforeEach
    public void setUp() {
        manager = new Ward_Manager();
    }

    @Test
    public void testAddPatient() {
        Patient_Management patient = new Patient_Management(
            "P001", 28,"Sipho", "Nkosi","Cardiac Arrest", Patient_Category.INPATIENT,"Male");
        assertTrue(manager.addPatient(patient), "Patient should be added successfully");
        assertNotNull(manager.findPatientById("P001"), "Patient should be found in system");
    }

    @Test
    public void testDeletePatient() {
        Patient_Management patient = new Patient_Management(
            "P002",34, "Lerato", "Mokoena","Severe Asthma", Patient_Category.OUTATIENT,"Female"
        );
        manager.addPatient(patient);

        boolean deleted = manager.deletePatient("P002");
        assertTrue(deleted, "Patient record should be deleted successfully");
        assertNull(manager.findPatientById("P002"), "Deleted patient should no longer exist");
    }

    @Test
    public void testFindPatientById_NotFound() {
        Patient_Management patient = manager.findPatientById("P999");
        assertNull(patient, "Non-existent patient ID should return null");
     }

   } 
}
