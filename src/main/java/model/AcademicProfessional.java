package model;

/**
 * Represents an academic professional user in the system.
 * This class extends the abstract User class and provides additional attributes specific to an academic professional.
 */
public class AcademicProfessional extends User {

    /**
     * The name of the academic professional.
     */
    private String name;

    /**
     * The current institution where the academic professional is affiliated.
     */
    private String currentInstitution;

    /**
     * The academic position held by the academic professional.
     */
    private String academicPosition;

    /**
     * Constructs an AcademicProfessional object.
     *
     * @param name              The name of the academic professional.
     * @param email             The email address of the academic professional.
     * @param password          The password for the academic professional's account.
     * @param currentInstitution The current institution where the academic professional is affiliated.
     * @param academicPosition   The academic position held by the academic professional.
     */
    public AcademicProfessional(String name, String email, String password, String currentInstitution, String academicPosition) {
        super(email, password, "AcademicProfessional");
        this.name = name;
        this.currentInstitution = currentInstitution;
        this.academicPosition = academicPosition;
    }

    /**
     * Gets the name of the academic professional.
     *
     * @return The name of the academic professional.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the current institution where the academic professional is affiliated.
     *
     * @return The current institution of the academic professional.
     */
    public String getCurrentInstitution() {
        return currentInstitution;
    }

    /**
     * Gets the academic position held by the academic professional.
     *
     * @return The academic position of the academic professional.
     */
    public String getAcademicPosition() {
        return academicPosition;
    }

    /**
     * Displays the user role for this user type.
     * This implementation outputs "Role: Academic Professional".
     */
    @Override
    public void displayUserRole() {
        System.out.println("Role: Academic Professional");
    }
}
