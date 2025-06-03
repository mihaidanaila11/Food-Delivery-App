package Menu.Options.Restaurants.EditRestaurant;

import Auth.AppContext;
import Menu.MenuOption;
import Stores.RestaurantOperations;
import Users.User;

import java.sql.SQLException;

public class ChangeName extends MenuOption {
    public ChangeName() {
        this.setLabel("Change Restaurant Name");
        this.addNeededRole(User.Roles.OWNER);
    }
    @Override
    public void action(AppContext ctx) {
        System.out.println("Enter new restaurant name:");
        String newName = Menu.Menu.getStringInput();

        try{
            RestaurantOperations.changeName(ctx.getEditingRestaurant(), newName, ctx.getDb());
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }
}
