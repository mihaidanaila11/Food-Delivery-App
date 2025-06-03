package Menu.Options.Restaurants.EditRestaurant;

import Auth.AppContext;
import Menu.Menu;
import Menu.MenuOption;
import Menu.Options.Cancel;

import java.util.ArrayList;

public class EditRestaurant extends MenuOption {
    @Override
    public void action(AppContext ctx) {
        ArrayList<MenuOption> options = new ArrayList<>();

        options.add(new AddProduct());
        options.add(new ChangeName());
        options.add(new Cancel());

        Menu menu = new Menu(options);
        menu.show(ctx);
    }
}
