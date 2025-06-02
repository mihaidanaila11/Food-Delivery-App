package Exceptions.MenuExceptions;

public class InvalidInput extends RuntimeException {
    public InvalidInput() {
        super("Invalid country selection. Please try again.");
    }
}
