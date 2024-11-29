package model;

public class AcademicInstitution extends User {
    private String institutionName;
    private String registrationEmail;

    public AcademicInstitution(String name, String email, String password, String institutionName) {
        super(name, email, password, "AcademicInstitution");
        this.institutionName = institutionName;
        this.registrationEmail = email;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    @Override
    public void displayUserRole() {
        System.out.println("Role: Academic Institution");
    }
}
