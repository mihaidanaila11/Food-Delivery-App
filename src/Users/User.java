package Users;

import Auth.PasswordHash;

public class User {
    private static int idCount = 0;
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private PasswordHash passwordHash;

    public User(String firstName, String lastName, String email, PasswordHash passwordHash) {
        this.id = String.valueOf(idCount);
        idCount++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordHash = passwordHash;
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
}
