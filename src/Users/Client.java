package Users;

import Auth.PasswordHash;
import Location.Location;
import Products.Order;

import java.util.List;

public class Client extends User{
    private List<Order> orderHistory;
    private Location location;

    public Client(String firstName, String lastName, String email, PasswordHash passwordHash) {
        super(firstName, lastName, email, passwordHash);
    }
}
