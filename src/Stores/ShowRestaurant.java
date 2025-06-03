package Stores;
import Products.Product;

public class ShowRestaurant {

    public static void show(Restaurant restaurant) {
        System.out.println("Restaurant Name: " + restaurant.getName());
        System.out.println("Location: " + restaurant.getLocation().getCity() + ", " + restaurant.getLocation().getStreet());
        System.out.println("Description: " + (restaurant.getDescription() != null ? restaurant.getDescription() : "No description available"));

        if (restaurant.getProducts() != null && !restaurant.getProducts().isEmpty()) {
            System.out.println("Products:");
            for (Product product : restaurant.getProducts()) {
                System.out.println("- " + product.getName() + ": $" + product.getPrice());
            }
        } else {
            System.out.println("No products available in this restaurant.");
        }
    }
}
