package Menu.Options.Cart;

import Auth.AppContext;
import Menu.Menu;
import Menu.MenuOption;
import Products.Product;
import Users.UserCart;

import java.util.ArrayList;

public class ViewCart extends MenuOption {
    public ViewCart(){
        this.setLabel("View Cart");
    }

    @Override
    public void action(AppContext ctx) {
        UserCart cart = ctx.getAuth().getLoggedClient().getCart();

        if(cart.isEmpty()){
            System.out.println("Your cart is empty.");
            return;
        }

        System.out.println("Your cart contains the following items:");
        for (Product item : cart.getProducts()) {
            System.out.println(item.getName() + " - " + cart.getQuantity(item) + " x " + item.getPrice() + "$");
        }

        ArrayList<MenuOption> options = new ArrayList<>();
        options.add(new PlaceOrder());

        Menu menu = new Menu(options);
        menu.show(ctx, true);
    }
}
