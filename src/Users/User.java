package Users;

import Auth.PasswordHash;

import java.util.ArrayList;
import java.util.HashSet;

public class User {
    private static int idCount = 0;
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private PasswordHash passwordHash;

    public enum Roles {USER, ADMIN}
    private HashSet<Roles> userRoles;

    public User(String firstName, String lastName, String email, PasswordHash passwordHash) {
        this.id = String.valueOf(idCount);
        idCount++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.userRoles = new HashSet<Roles>();
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public PasswordHash getPasswordHash() { return passwordHash; }

    public HashSet<Roles> getRoles() { return userRoles; }
    public void addRole(Roles userRole) { this.userRoles.add(userRole); }
}
