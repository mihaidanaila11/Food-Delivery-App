package Exceptions.UserExceptions;

public class LocationNotSet extends RuntimeException {
    public LocationNotSet() {
        super("Please set the location before creating an order.");
    }
}
