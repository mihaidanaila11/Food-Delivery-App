package Exceptions.CartExceptions;

public class EmptyCart extends RuntimeException {
    public EmptyCart() {
        super("Cart is empty: ");
    }
}
