package Menu.Options.Restaurants;

import Auth.AppContext;
import Menu.MenuOption;
import Menu.Options.Restaurants.ViewRestaurant;
import Stores.Restaurant;
import Stores.RestaurantOperations;
import Stores.ShowRestaurant;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class ViewRestaurants extends MenuOption {
    public ViewRestaurants() {
        this.setLabel("View Restaurants");
    }

    @Override
    public void action(AppContext ctx) {
        ArrayList<Restaurant> restaurants;
        try{
            restaurants = RestaurantOperations.getRestaurantsByCity(
                    ctx.getAuth().getLoggedClient().getLocation().getCity(), ctx.getDb()
            );
        } catch (SQLException e){
            System.out.println(e.getMessage());
            return;
        }

        if(restaurants.isEmpty()){
            System.out.println("No restaurants found in your city.");
            return;
        }

        System.out.println("See a restaurant from your city:");

        Restaurant selectedRestaurant;
        try{
            selectedRestaurant = Menu.Menu.selectFromList(restaurants);
        } catch (Exception e){
            return;
        }


        ctx.setEditingRestaurant(selectedRestaurant);
        MenuOption viewRestaurant = new ViewRestaurant();
        viewRestaurant.action(ctx);
    }
}
