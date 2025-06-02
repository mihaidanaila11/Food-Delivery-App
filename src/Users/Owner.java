package Users;
import Auth.PasswordHash;
import Stores.Restaurant;
import java.util.List;


public class Owner extends User {
    private List<Restaurant> restaurants;

    public Owner(String firstName, String lastName, String email, PasswordHash passwordHash, List<Restaurant> restaurants,
                 boolean regComplete) {
        super(firstName, lastName, email, passwordHash, regComplete);
        this.restaurants = restaurants;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }
}
