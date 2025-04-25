package Users;

import Location.Address;
import Products.Order;

import java.util.List;

public class Client extends User{
    private List<Order> orderHistory;
    private Address address;

    public Client(String firstName, String lastName, String email, byte[] passwordHash) {
        super(firstName, lastName, email, passwordHash);
    }
}
