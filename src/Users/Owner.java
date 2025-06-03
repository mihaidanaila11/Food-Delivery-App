package Users;
import Auth.PasswordHash;
import Stores.Restaurant;

import java.util.ArrayList;
import java.util.List;


public class Owner extends User {
    private ArrayList<Restaurant> restaurants;

    public Owner(String firstName, String lastName, String email, PasswordHash passwordHash, ArrayList<Restaurant> restaurants,
                 boolean regComplete) {
        super(firstName, lastName, email, passwordHash, regComplete);
        this.restaurants = restaurants;
    }


    public Owner(User user) {
        super(user);
        this.restaurants = new ArrayList<>();
    }

    public Owner(User user, ArrayList<Restaurant> restaurants) {
        super(user);
        this.restaurants = restaurants;
    }

    public ArrayList<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(ArrayList<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }
    public void addRestaurant(Restaurant restaurant) {
        this.restaurants.add(restaurant);
    }

    public void removeRestaurant(Restaurant restaurant) {
        this.restaurants.remove(restaurant);
    }
}
