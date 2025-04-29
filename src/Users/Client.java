package Users;

import Auth.PasswordHash;
import Location.Address;
import Products.Order;

import java.util.List;

public class Client extends User{
    private List<Order> orderHistory;
    private Address address;

    public Client(String firstName, String lastName, String email, PasswordHash passwordHash) {
        super(firstName, lastName, email, passwordHash);
    }
}
