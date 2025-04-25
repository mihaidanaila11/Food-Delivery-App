package Exceptions.AuthExceptions;

public class UserAlreadyExsists extends Exception {
    public UserAlreadyExsists() {
        super("User is already exsists");
    }

    public UserAlreadyExsists(String message) {
        super(message);
    }
}
