package Menu.Options.Owner;

import Auth.AppContext;
import Menu.MenuOption;
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

        for(Restaurant restaurant : restaurants){
            System.out.println(" - " + restaurant.getName());
        }
    }

}
