package Menu.Options.Restaurants;

import Auth.AppContext;
import Exceptions.MenuExceptions.CancelInput;
import Menu.Menu;
import Menu.MenuOption;
import Menu.Options.Products.ViewProduct;
import Products.Product;
import Stores.ShowRestaurant;

import java.util.ArrayList;

public class ViewRestaurant extends MenuOption {
public ViewRestaurant() {
        this.setLabel("View Restaurant Details");
    }

    @Override
    public void action(AppContext ctx) {

        ShowRestaurant.show(ctx.getEditingRestaurant());

        System.out.println("View a product:");
        Product selectedProduct;

        try{
            selectedProduct = Menu.selectFromList(ctx.getEditingRestaurant().getProducts());
        } catch (CancelInput e) {
            return;
        }

        ctx.setEditingProduct(selectedProduct);

        ViewProduct viewProduct = new ViewProduct();
        viewProduct.action(ctx);
    }
}
