package Menu.Options.Restaurants.EditRestaurant;

import Auth.AppContext;
import Menu.MenuOption;
import Stores.RestaurantOperations;
import Users.User;

import java.sql.SQLException;

public class EditDescription extends MenuOption {
    public EditDescription() {
        this.setLabel("Edit Restaurant Description");
        this.addNeededRole(User.Roles.OWNER);
    }

    @Override
    public void action(AppContext ctx) {
        System.out.println("Enter new restaurant description:");
        String newDescription = Menu.Menu.getStringInput();

        try{
            RestaurantOperations.changeDescription(ctx.getEditingRestaurant(), newDescription, ctx.getDb());
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
