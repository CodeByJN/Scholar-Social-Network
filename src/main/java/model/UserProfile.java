package model;

/**
 * Represents a user's profile in the system.
 * This class stores various details about a user such as their contact information, educational background, and position.
 */
public class UserProfile {
    private final String email;
    private final String name;
    private final String educationBackground;
    private final String areaOfExpertise;
    private final String address;
    private final String coursesOfferedByTerm;
    private final String currentInstitution;
    private final String academicPosition;
    private final String institutionName;

    /**
     * Constructs a {@code UserProfile} object with all fields initialized.
     *
     * @param email               the email address of the user.
     * @param name                the name of the user.
     * @param educationBackground the educational background of the user.
     * @param areaOfExpertise     the area of expertise of the user.
     * @param address             the address of the academic institution (if applicable).
     * @param coursesOfferedByTerm the courses offered by the academic institution, categorized by term.
     * @param currentInstitution  the current institution of the academic professional.
     * @param academicPosition    the academic position held by the academic professional.
     * @param institutionName     the name of the academic institution.
     */
    public UserProfile(String email, String name, String educationBackground, String areaOfExpertise, String address, String coursesOfferedByTerm, String currentInstitution, String academicPosition, String institutionName) {
        this.email = email;
        this.name = name;
        this.educationBackground = educationBackground;
        this.areaOfExpertise = areaOfExpertise;
        this.address = address;
        this.coursesOfferedByTerm = coursesOfferedByTerm;
        this.currentInstitution = currentInstitution;
        this.academicPosition = academicPosition;
        this.institutionName = institutionName;
    }

    /**
     * Gets the email address of the user.
     *
     * @return the email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the name of the user.
     *
     * @return the name of the user.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the educational background of the user.
     *
     * @return the educational background.
     */
    public String getEducationBackground() {
        return educationBackground;
    }

    /**
     * Gets the area of expertise of the user.
     *
     * @return the area of expertise.
     */
    public String getAreaOfExpertise() {
        return areaOfExpertise;
    }

    /**
     * Gets the address of the academic institution.
     *
     * @return the address of the institution.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Gets the courses offered by the academic institution categorized by term.
     *
     * @return the courses offered by term.
     */
    public String getCoursesOfferedByTerm() {
        return coursesOfferedByTerm;
    }

    /**
     * Gets the current institution of the academic professional.
     *
     * @return the current institution.
     */
    public String getCurrentInstitution() {
        return currentInstitution;
    }

    /**
     * Gets the academic position held by the academic professional.
     *
     * @return the academic position.
     */
    public String getAcademicPosition() {
        return academicPosition;
    }

    /**
     * Gets the name of the academic institution.
     *
     * @return the name of the institution.
     */
    public String getInstitutionName() {
        return institutionName;
    }
}
