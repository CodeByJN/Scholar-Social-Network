package model;

/**
 * Represents an academic institution user in the system.
 * This class extends the abstract User class and provides additional attributes specific to an academic institution.
 */
public class AcademicInstitution extends User {

    /**
     * The name of the academic institution.
     */
    private String institutionName;

    /**
     * Constructs an AcademicInstitution object.
     *
     * @param email           The email address of the institution.
     * @param password        The password for the institution's account.
     * @param institutionName The name of the academic institution.
     */
    public AcademicInstitution(String email, String password, String institutionName) {
        super(email, password, "AcademicInstitution");
        this.institutionName = institutionName;
    }

    /**
     * Gets the name of the academic institution.
     *
     * @return The name of the institution.
     */
    @Override
    public String getName() {
        return institutionName;
    }


    /**
     * Displays the user role for this user type.
     * This implementation outputs "Role: Academic Institution".
     */
    @Override
    public void displayUserRole() {
        System.out.println("Role: Academic Institution");
    }
}
