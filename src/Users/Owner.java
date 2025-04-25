package Users;
import Stores.Restaurant;
import java.util.List;


public class Owner extends User {
    private List<Restaurant> restaurants;

    public Owner(String firstName, String lastName, String email, byte[] passwordHash, List<Restaurant> restaurants) {
        super(firstName, lastName, email, passwordHash);
        this.restaurants = restaurants;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }
}
