package Menu.Options.Owner;

import Auth.AppContext;
import Exceptions.MenuExceptions.CancelInput;
import Menu.MenuOption;
import Menu.Options.Restaurants.EditRestaurant.EditRestaurant;
import Stores.Restaurant;
import Users.Owner;

import java.util.ArrayList;

public class OwnedRestaurants extends MenuOption {
    public OwnedRestaurants() {
        this.setLabel("Owned Restaurants");
    }

    @Override
    public void action(AppContext ctx) {
        System.out.println("Owned Restaurants:");
        Owner owner = ctx.getAuth().getLoggedOwner();

        ArrayList<Restaurant> restaurants = owner.getRestaurants();

        if (restaurants.isEmpty()) {
            System.out.println("You do not own any restaurants.");
            return;
        }

        System.out.println("Select a restaurant to manage:");

        Restaurant selectedRestaurant;
        try{
            selectedRestaurant = Menu.Menu.selectFromList(restaurants);
        } catch (Exception e) {
            return;
        }

        ctx.setEditingRestaurant(selectedRestaurant);

        MenuOption editRestaurant = new EditRestaurant();
        editRestaurant.action(ctx);

    }

}
