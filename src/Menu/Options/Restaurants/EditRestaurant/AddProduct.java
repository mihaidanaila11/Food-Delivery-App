package Menu.Options.Restaurants.EditRestaurant;

import Auth.AppContext;
import Exceptions.MenuExceptions.InvalidInput;
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
        String name, description;
        int price;
        try{
            System.out.println("Product name: ");
            name = Menu.Menu.getStringInput();

            System.out.println("Product price: ");
            price = Menu.Menu.getIntInput();

            System.out.println("Product description: ");
            description = Menu.Menu.getStringInput();
        } catch (InvalidInput e){
            System.out.println("Invalid input. Please try again.");
            return;
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            return;
        }

        try {
            ProductCreator productCreator = new ProductCreator();
            Product product = productCreator.createProduct(name, price, description);
            ProductOperations.addProduct(product, ctx.getEditingRestaurant(), ctx.getDb());

            System.out.println("Product added successfully.");
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("Error adding product: " + e.getMessage());
        }
    }
}
