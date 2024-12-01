package model;

/**
 * Represents a generic user in the system.
 * This abstract class serves as the base for specific user types like academic professionals or academic institutions.
 */
public abstract class User {

    /**
     * The email address of the user.
     */
    private String email;

    /**
     * The password for the user's account.
     */
    private String password;

    /**
     * The type of the user (e.g., AcademicProfessional, AcademicInstitution).
     */
    private String userType;

    /**
     * Constructs a User object.
     *
     * @param email     The email address of the user.
     * @param password  The password for the user's account.
     * @param userType  The type of user (e.g., "AcademicProfessional" or "AcademicInstitution").
     */
    public User(String email, String password, String userType) {
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    /**
     * Gets the email address of the user.
     *
     * @return The email address of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the password of the user.
     *
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the user type.
     *
     * @return The type of the user.
     */
    public String getUserType() {
        return userType;
    }

    /**
     * Displays the user role.
     * This method must be implemented by all subclasses to provide specific role information.
     */
    public abstract void displayUserRole();

    /**
     * Displays the details of the user including email and user role.
     * This method cannot be overridden by subclasses.
     */
    public final void displayUserDetails() {
        System.out.println("User Email: " + email);
        displayUserRole();
    }
}
