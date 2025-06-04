package Users;

import Location.Location;
import Orders.Order;

import java.util.ArrayList;
import java.util.List;

public class Client extends User{
    private ArrayList<Order> activeOrders;
    private Location location;
    private UserCart cart;

    public Client(User user, Location location) {
        super(user);
        this.location = location;
        this.cart = new UserCart();
        this.activeOrders = new ArrayList<>();
    }

    public void setLocation(Location location) { this.location = location; }
    public Location getLocation() { return location; }

    public UserCart getCart() {
        return cart;
    }

    public void addActiveOrder(Order order) {
        this.activeOrders.add(order);
    }

    public ArrayList<Order> getActiveOrders() {
        return activeOrders;
    }
}
