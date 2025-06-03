package Products;

import Auth.AppContext;
import Stores.Restaurant;
import database.DatabaseHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class ProductOperations {
    public static void addProduct(Product product, Restaurant restaurant, DatabaseHandler db)  throws SQLException {
        db.insertProduct(product, restaurant);

        restaurant.addProduct(product);
    }

    public static ArrayList<Product> getProductsByRestaurant(Restaurant restaurant, DatabaseHandler db) throws SQLException {
        ResultSet rs = db.fetchProductsByRestaurant(restaurant);
        ArrayList<Product> products = new ArrayList<>();

        while(rs.next()){
            products.add(new Product(
                    rs.getString("ProductName"),
                    rs.getFloat("Price"),
                    rs.getString("ProductDescription"),
                    UUID.fromString(rs.getString("ProductID")),
                    restaurant
            ));
        }

        return products;
    }

    private static void validateProductInput(Product product, String string) {
        if (product == null || string == null || string.isEmpty()) {
            throw new IllegalArgumentException("Invalid restaurant or name");
        }
    }

    public static void changeName(Product product, String newName, DatabaseHandler db) throws SQLException {
        validateProductInput(product, newName);

        product.setName(newName);
        db.updateProductName(product, newName);
    }

    public static void changePrice(Product product, Float newPrice, DatabaseHandler db) throws SQLException {
        if (product == null) {
            throw new IllegalArgumentException("Invalid restaurant or name");
        }

        product.setPrice(newPrice);
        db.updateProductPrice(product, newPrice);
    }

    public static void changeDescription(Product product, String newDescription, DatabaseHandler db) throws SQLException {
        validateProductInput(product, newDescription);

        product.setDescription(newDescription);
        db.updateProductDescription(product, newDescription);
    }

    public static void delete(Product product, DatabaseHandler db, AppContext ctx) throws SQLException {
        if (product == null) {
            throw new IllegalArgumentException("Invalid restaurant or name");
        }

        db.deleteProduct(product);
        ctx.getEditingRestaurant().removeProduct(product);
    }
}
