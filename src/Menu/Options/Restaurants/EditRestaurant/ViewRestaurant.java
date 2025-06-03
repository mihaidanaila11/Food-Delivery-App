package Menu.Options.Restaurants.EditRestaurant;

import Auth.AppContext;
import Menu.MenuOption;
import Stores.ShowRestaurant;

public class ViewRestaurant extends MenuOption {
public ViewRestaurant() {
        this.setLabel("View Restaurant Details");
    }

    @Override
    public void action(AppContext ctx) {
        ShowRestaurant.show(ctx.getEditingRestaurant());
    }
}
