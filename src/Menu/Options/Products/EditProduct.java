package Menu.Options.Products;

import Auth.AppContext;
import Menu.Menu;
import Menu.MenuOption;

import java.util.ArrayList;

public class EditProduct extends MenuOption {
    public EditProduct() {
        this.setLabel("Edit Product");
    }
    @Override
    public void action(AppContext ctx) {
        ArrayList<MenuOption> options = new ArrayList<>();

        options.add(new EditName());
        options.add(new EditPrice());
        options.add(new EditDescription());
        options.add(new DeleteProduct());

        Menu menu = new Menu(options);
        menu.show(ctx, true);
    }
}
