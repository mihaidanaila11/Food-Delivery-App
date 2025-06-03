package Menu.Options.Restaurants.EditRestaurant;

import Auth.AppContext;
import Menu.Menu;
import Menu.MenuOption;
import Menu.Options.Cancel;
import Menu.Options.Restaurants.ViewRestaurants;
import Stores.ShowRestaurant;

import java.util.ArrayList;

public class EditRestaurant extends MenuOption {
    @Override
    public void action(AppContext ctx) {
        ArrayList<MenuOption> options = new ArrayList<>();

        options.add(new ViewRestaurant());
        options.add(new AddProduct());
        options.add(new ChangeName());
        options.add(new EditDescription());
        options.add(new DeleteRestaurant());

        Menu menu = new Menu(options);
        menu.show(ctx, true);
    }
}
