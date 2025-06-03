package Menu.Options.Restaurants.EditRestaurant;

import Auth.AppContext;
import Menu.MenuOption;
import Products.Product;
import Products.ProductCreator;
import Products.ProductOperations;

import java.sql.SQLException;

public class AddProduct extends MenuOption {
    public AddProduct(){
        this.setLabel("Add Product to Restaurant");
    }

    @Override
    public void action(AppContext ctx) {
        System.out.println("Product name: ");
        String name = Menu.Menu.getStringInput();

        System.out.println("Product price: ");
        int price = Menu.Menu.getIntInput();

        System.out.println("Product description: ");
        String description = Menu.Menu.getStringInput();

        ProductCreator productCreator = new ProductCreator();
        try {
            Product product = productCreator.createProduct(name, price, description);
            ProductOperations.addProduct(product, ctx.getEditingRestaurant(), ctx.getDb());

            System.out.println("Product added successfully.");
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("Error adding product: " + e.getMessage());
        }
    }
}
