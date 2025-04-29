package Exceptions.AuthExceptions;

public class UserDoesNotExist extends RuntimeException {
    public UserDoesNotExist() {
        super("User does not exist");
    }
}
