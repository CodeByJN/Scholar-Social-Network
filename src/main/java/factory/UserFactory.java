package factory;

import model.User;
import model.AcademicProfessional;
import model.AcademicInstitution;

public class UserFactory {
    public User createUser(String userType, String name, String email, String password, String extraInfo) {
        if (userType.equalsIgnoreCase("AcademicProfessional")) {
            return new AcademicProfessional(name, email, password, extraInfo, "Unknown Position");
        } else if (userType.equalsIgnoreCase("AcademicInstitution")) {
            return new AcademicInstitution(name, email, password, extraInfo);
        }
        return null;
    }
}
