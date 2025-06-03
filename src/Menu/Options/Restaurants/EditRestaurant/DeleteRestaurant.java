package Menu.Options.Restaurants.EditRestaurant;

import Auth.AppContext;
import Menu.MenuOption;
import Stores.RestaurantOperations;
import Users.User;

import java.sql.SQLException;

public class DeleteRestaurant extends MenuOption {
    public DeleteRestaurant() {
        this.setLabel("Delete Restaurant");
        this.addNeededRole(User.Roles.OWNER);
    }

    @Override
    public void action(AppContext ctx) {
        try{
            RestaurantOperations.delete(ctx.getEditingRestaurant(), ctx.getDb(), ctx);
        } catch (SQLException e){
            System.out.println(e.getMessage());
            return;
        }

        ctx.setEditingRestaurant(null);
    }
}
