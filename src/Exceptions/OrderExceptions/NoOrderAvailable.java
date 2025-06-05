package Exceptions.OrderExceptions;

public class NoOrderAvailable extends RuntimeException {
    public NoOrderAvailable() {
        super("No available orders at the moment.");
    }
}
