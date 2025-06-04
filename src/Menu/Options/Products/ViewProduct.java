package Menu.Options.Products;

import Auth.AppContext;
import Menu.MenuOption;
import Products.Product;
import Menu.Menu;

import java.util.ArrayList;

public class ViewProduct extends MenuOption {
    public ViewProduct() {
        this.setLabel("View Product");
    }

    @Override
    public void action(AppContext ctx) {
        Product product = ctx.getEditingProduct();

        product.show();

        ArrayList<MenuOption> options = new ArrayList<>();
        options.add(new AddToCart());

        Menu menu = new Menu(options);
        menu.show(ctx, true);
    }
}
