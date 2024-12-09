package builder;
/**
 * course: CST8288
 * name: Ningyi Wang
 * Student ID: 041120798
 */
import java.util.List;
import java.util.Map;

/**
 * Builder class for constructing {@link InstitutionProfile} objects.
 */
public class InstitutionProfileBuilder {
    private String address;
    private Map<String, List<String>> coursesOfferedByTerm;

    /**
     * Sets the address of the institution.
     *
     * @param address The address of the institution.
     * @return The current {@link InstitutionProfileBuilder} instance.
     */
    public InstitutionProfileBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    /**
     * Sets the courses offered by term for the institution.
     *
     * @param coursesOfferedByTerm A map where each key is a term and the value is a list of courses offered in that term.
     * @return The current {@link InstitutionProfileBuilder} instance.
     */
    public InstitutionProfileBuilder setCoursesOfferedByTerm(Map<String, List<String>> coursesOfferedByTerm) {
        this.coursesOfferedByTerm = coursesOfferedByTerm;
        return this;
    }

    /**
     * Builds and returns an {@link InstitutionProfile} instance with the set properties.
     *
     * @return A new {@link InstitutionProfile} instance.
     */
    public InstitutionProfile build() {
        return new InstitutionProfile(address, coursesOfferedByTerm);
    }
}
