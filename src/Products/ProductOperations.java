package Products;

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
                    UUID.fromString(rs.getString("ProductID"))
            ));
        }

        return products;
    }
}
