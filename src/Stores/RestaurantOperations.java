package Stores;

import Auth.AppContext;
import Location.City;
import Location.Location;
import Products.Product;
import Products.ProductOperations;
import Users.Owner;
import database.DatabaseHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import static Logger.Logger.logOperation;

public class RestaurantOperations {
    public static void addRestaurant(Restaurant restaurant, DatabaseHandler db) throws SQLException {
        restaurant.setID(UUID.randomUUID());

        db.insertRestaurant(restaurant);

        db.addRestaurantOwner(restaurant, restaurant.getOwner());

        logOperation("Added restaurant " + restaurant.getName() + " with ID: " + restaurant.getID());
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

            restaurant.setProducts(ProductOperations.getProductsByRestaurant(restaurant, db));
            restaurants.add(restaurant);

            logOperation("Fetched restaurant: " + name);
        }

        return restaurants;
    }

    public static void changeName(Restaurant restaurant, String newName, DatabaseHandler db) throws SQLException {
        if (restaurant == null || newName == null || newName.isEmpty()) {
            throw new IllegalArgumentException("Invalid restaurant or name");
        }

        logOperation("Changing restaurant name from " + restaurant.getName() + " to " + newName);

        restaurant.setName(newName);
        db.updateRestaurantName(restaurant, newName);

    }

    public static void changeDescription(Restaurant restaurant, String newDescription, DatabaseHandler db) throws SQLException {
        if (restaurant == null || newDescription == null || newDescription.isEmpty()) {
            throw new IllegalArgumentException("Invalid restaurant or name");
        }

        logOperation("Changing restaurant description from " + restaurant.getDescription() + " to " + newDescription);

        restaurant.setDescription(newDescription);
        db.updateRestaurantDescription(restaurant, newDescription);
    }

    public static void delete(Restaurant restaurant, DatabaseHandler db, AppContext ctx) throws SQLException {
        if (restaurant == null) {
            throw new IllegalArgumentException("Invalid restaurant");
        }

        logOperation("Deleting restaurant: " + restaurant.getName());

        ctx.getAuth().getLoggedOwner().removeRestaurant(restaurant);
        db.deleteRestaurant(restaurant);
    }
}
