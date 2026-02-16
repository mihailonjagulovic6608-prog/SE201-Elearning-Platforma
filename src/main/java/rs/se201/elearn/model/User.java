package rs.se201.elearn.model;

public class User {
    public int id;
    public String email;
    public String role;
    public boolean disabled;

    public User(int id, String email, String role, boolean disabled) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.disabled = disabled;
    }
}
