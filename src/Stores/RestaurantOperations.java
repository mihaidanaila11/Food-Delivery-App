package Stores;

import Users.Owner;
import database.DatabaseHandler;

import java.sql.SQLException;
import java.util.UUID;

public class RestaurantOperations {
    public static void addRestaurant(Restaurant restaurant, DatabaseHandler db) throws SQLException {
        restaurant.setID(UUID.randomUUID());

        db.insertRestaurant(restaurant);

        db.addRestaurantOwner(restaurant, restaurant.getOwner());
    }
}
