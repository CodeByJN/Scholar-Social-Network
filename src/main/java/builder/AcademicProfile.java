package builder;

/**
 * Represents an academic profile with details about the educational background and area of expertise.
 */
class AcademicProfile {
    private final String educationBackground;
    private final String areaOfExpertise;

    /**
     * Constructs an AcademicProfile with the given education background and area of expertise.
     *
     * @param educationBackground The educational background of the individual.
     * @param areaOfExpertise     The area of expertise of the individual.
     */
    public AcademicProfile(String educationBackground, String areaOfExpertise) {
        this.educationBackground = educationBackground;
        this.areaOfExpertise = areaOfExpertise;
    }

    /**
     * Displays the academic profile information.
     * Prints the educational background and area of expertise to the console.
     */
    public void displayProfile() {
        System.out.println("Education Background: " + educationBackground);
        System.out.println("Area of Expertise: " + areaOfExpertise);
    }
}
