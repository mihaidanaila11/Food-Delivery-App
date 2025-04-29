package Exceptions.AuthExceptions;

public class IncorrectPassword extends RuntimeException {
    public IncorrectPassword() {
        super("Incorrect password");
    }
}
