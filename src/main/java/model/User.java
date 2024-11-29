package model;

public abstract class User {
    private String name;
    private String email;
    private String password;
    private String userType;

    public User(String name, String email, String password, String userType) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUserType() {
        return userType;
    }

    public abstract void displayUserRole();

    public final void displayUserDetails() {
        System.out.println("User Name: " + name);
        System.out.println("User Email: " + email);
        displayUserRole();
    }
}
