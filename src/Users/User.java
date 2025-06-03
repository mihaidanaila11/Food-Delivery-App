package Users;

import Auth.PasswordHash;
import Stores.Restaurant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

public class User {
    private final UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private final PasswordHash passwordHash;
    private boolean regComplete;

    public enum Roles {USER, ADMIN, OWNER, CLIENT}
    private HashSet<Roles> userRoles;

    public User(User user) {
        this.id = user.id;
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.email = user.email;
        this.passwordHash = user.passwordHash;
        this.userRoles = new HashSet<>(user.userRoles);
        this.regComplete = user.regComplete;
    }

    public User(String firstName, String lastName, String email, PasswordHash passwordHash, boolean regComplete) {
        this.id = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.userRoles = new HashSet<>();
        this.regComplete = regComplete;
    }

    public User(String id, String firstName, String lastName, String email, PasswordHash passwordHash, boolean regComplete) {
        this.id = UUID.fromString(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.userRoles = new HashSet<>();
        this.regComplete = regComplete;
    }

    public User(UUID id, String firstName, String lastName, String email, PasswordHash passwordHash, boolean regComplete) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.userRoles = new HashSet<>();
        this.regComplete = regComplete;
    }

    public UUID getId() {
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

    public User setRegComplete(boolean regComplete) {
        this.regComplete = regComplete;

        return this;
    }

    public boolean isRegComplete() {
        return regComplete;
    }
}
