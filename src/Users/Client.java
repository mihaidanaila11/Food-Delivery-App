package Users;

import Auth.PasswordHash;
import Location.Location;
import Products.Order;

import java.util.List;
import java.util.UUID;

public class Client extends User{
    private List<Order> orderHistory;
    private Location location;

    public Client(String firstName, String lastName, String email, PasswordHash passwordHash,
                  boolean regComplete, Location location) {
        super(firstName, lastName, email, passwordHash, regComplete);
        this.location = location;
    }

    public Client(String id, String firstName, String lastName, String email, PasswordHash passwordHash,
                  boolean regComplete, Location location) {
        super(id, firstName, lastName, email, passwordHash, regComplete);
        this.location = location;
    }

    public Client(User user, Location location) {
        super(user);
        this.location = location;
    }

    public void setLocation(Location location) { this.location = location; }
    public Location getLocation() { return location; }

}
