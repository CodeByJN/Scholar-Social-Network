package model;

public class AcademicProfessional extends User {
    private String currentInstitution;
    private String academicPosition;

    public AcademicProfessional(String name, String email, String password, String currentInstitution, String academicPosition) {
        super(name, email, password, "AcademicProfessional");
        this.currentInstitution = currentInstitution;
        this.academicPosition = academicPosition;
    }

    public String getCurrentInstitution() {
        return currentInstitution;
    }

    public String getAcademicPosition() {
        return academicPosition;
    }

    @Override
    public void displayUserRole() {
        System.out.println("Role: Academic Professional");
    }
}
