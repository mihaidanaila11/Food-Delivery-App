package Stores;

import Location.City;
import Location.Location;
import Users.Owner;
import database.DatabaseHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class RestaurantOperations {
    public static void addRestaurant(Restaurant restaurant, DatabaseHandler db) throws SQLException {
        restaurant.setID(UUID.randomUUID());

        db.insertRestaurant(restaurant);

        db.addRestaurantOwner(restaurant, restaurant.getOwner());
    }

    public static ArrayList<Restaurant> getRestaurantsByCity(City city, DatabaseHandler db) throws SQLException {
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        ResultSet rs = db.fetchRestaurantsByCity(city);

        while (rs.next()) {
            UUID id = UUID.fromString(rs.getString("RestaurantID"));
            String name = rs.getString("RestaurantName");
            Location location = db.fetchLocationById(rs.getInt("LocationID"));
            String description = rs.getString("Description");

            Restaurant restaurant = new Restaurant(name, location, description);
            restaurant.setID(id);
            restaurants.add(restaurant);
        }

        return restaurants;
    }
}
