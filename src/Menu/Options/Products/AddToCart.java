package Menu.Options.Products;

import Auth.AppContext;
import Menu.MenuOption;

public class AddToCart extends MenuOption {
    public AddToCart() {
        this.setLabel("Add to Cart");
    }

    @Override
    public void action(AppContext ctx) {
        System.out.println("Enter the quantity you want to add to the cart:");
        int quantity = Menu.Menu.getIntInput();


        ctx.getAuth().getLoggedClient().getCart().addToCart(ctx.getEditingProduct(), quantity);
    }
}
