package Users;

import Auth.PasswordHash;
import Location.Location;
import Products.Order;
import Products.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Client extends User{
    private List<Order> orderHistory;
    private Location location;
    private UserCart cart;

    public Client(User user, Location location) {
        super(user);
        this.location = location;
        this.cart = new UserCart();
    }

    public void setLocation(Location location) { this.location = location; }
    public Location getLocation() { return location; }

    public UserCart getCart() {
        return cart;
    }
}
