package Users;

import Auth.PasswordHash;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

public class User {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private PasswordHash passwordHash;

    public enum Roles {USER, ADMIN}
    private HashSet<Roles> userRoles;

    public User(String firstName, String lastName, String email, PasswordHash passwordHash) {
        this.id = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.userRoles = new HashSet<Roles>();
    }

    public User(String id, String firstName, String lastName, String email, PasswordHash passwordHash) {
        this.id = UUID.fromString(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.userRoles = new HashSet<Roles>();
    }

    public String getId() {
        return id.toString();
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
    public User addRole(Roles userRole) {
        this.userRoles.add(userRole);

        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserRoles(HashSet<Roles> userRoles) {
        this.userRoles = userRoles;
    }
}
