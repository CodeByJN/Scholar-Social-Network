package builder;

import java.util.List;
import java.util.Map;

/**
 * Represents an institution profile, containing address information and courses offered by term.
 */
class InstitutionProfile {
    private final String address;
    private final Map<String, List<String>> coursesOfferedByTerm;

    /**
     * Constructs an instance of {@link InstitutionProfile} with the specified address and courses offered by term.
     *
     * @param address The address of the institution.
     * @param coursesOfferedByTerm A map where each key is a term and the value is a list of courses offered in that term.
     */
    public InstitutionProfile(String address, Map<String, List<String>> coursesOfferedByTerm) {
        this.address = address;
        this.coursesOfferedByTerm = coursesOfferedByTerm;
    }

    /**
     * Displays the institution profile information, including address and courses offered.
     */
    public void displayProfile() {
        System.out.println("Address: " + address);
        System.out.println("Courses Offered:");
        for (Map.Entry<String, List<String>> entry : coursesOfferedByTerm.entrySet()) {
            System.out.println("Term: " + entry.getKey() + " Courses: " + entry.getValue());
        }
    }
}
