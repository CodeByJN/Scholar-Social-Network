package builder;

/**
 * Builder class for creating an instance of {@link AcademicProfile}.
 */
public class AcademicProfileBuilder {
    private String educationBackground;
    private String areaOfExpertise;

    /**
     * Sets the educational background for the academic profile.
     *
     * @param education The educational background to set.
     * @return The current instance of {@link AcademicProfileBuilder}.
     */
    public AcademicProfileBuilder setEducation(String education) {
        this.educationBackground = education;
        return this;
    }

    /**
     * Sets the area of expertise for the academic profile.
     *
     * @param expertise The area of expertise to set.
     * @return The current instance of {@link AcademicProfileBuilder}.
     */
    public AcademicProfileBuilder setExpertise(String expertise) {
        this.areaOfExpertise = expertise;
        return this;
    }

    /**
     * Builds and returns an instance of {@link AcademicProfile} using the provided data.
     *
     * @return A new {@link AcademicProfile} instance with the specified attributes.
     */
    public AcademicProfile build() {
        return new AcademicProfile(educationBackground, areaOfExpertise);
    }
}
