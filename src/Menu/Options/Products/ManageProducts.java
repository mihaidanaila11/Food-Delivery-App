package Menu.Options.Products;

import Auth.AppContext;
import Menu.MenuOption;
import Menu.Options.Restaurants.EditRestaurant.EditRestaurant;
import Products.Product;
import Stores.Restaurant;
import Users.Owner;

import java.util.ArrayList;

public class ManageProducts extends MenuOption {
    public ManageProducts() {
        this.setLabel("View Products");
    }

    @Override
    public void action(AppContext ctx) {
        Restaurant restaurant = ctx.getEditingRestaurant();

        ArrayList<Product> products = restaurant.getProducts();

        if (products.isEmpty()) {
            System.out.println("The restaurant has no products.");
            return;
        }

        System.out.println("Chose a product to manage:");

        Product selectedProduct;
        try{
            selectedProduct = Menu.Menu.selectFromList(products);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return;
        }

        ctx.setEditingProduct(selectedProduct);

        MenuOption editProduct = new EditProduct();
        editProduct.action(ctx);
    }
}
