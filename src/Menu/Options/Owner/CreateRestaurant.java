package Menu.Options.Owner;

import Auth.AppContext;
import Location.Location;
import Menu.MenuOption;
import Menu.Utils.LocationUtils;
import Stores.Restaurant;
import Stores.RestaurantOperations;
import Users.Owner;

import java.sql.SQLException;
import java.util.Scanner;

public class CreateRestaurant extends MenuOption {
    public CreateRestaurant() {
        this.setLabel("Create Restaurant");
    }

    @Override
    public void action(AppContext ctx) {

        System.out.println("What is the name of your restaurant?");
        String restaurantName = Menu.Menu.getStringInput();

        System.out.println("Where is your restaurant located?");
        Location location;
        try{
            location = LocationUtils.askForLocation(ctx);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        Restaurant restaurant = new Restaurant(
                restaurantName,
                location,
                ctx.getAuth().getLoggedOwner()
        );

        try{
            RestaurantOperations.addRestaurant(restaurant, ctx.getDb());
            ctx.getAuth().getLoggedOwner().addRestaurant(restaurant);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return;
        }

    }
}
