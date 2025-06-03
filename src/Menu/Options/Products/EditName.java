package Menu.Options.Products;

import Auth.AppContext;
import Menu.MenuOption;
import Products.ProductOperations;
import Users.User;

import java.sql.SQLException;

public class EditName extends MenuOption {
    public EditName() {
        this.setLabel("Edit Product Name");
        this.addNeededRole(User.Roles.OWNER);
    }

    @Override
    public void action(AppContext ctx) {
        System.out.println("Enter new product name:");
        String newName = Menu.Menu.getStringInput();

        try{
            ProductOperations.changeName(ctx.getEditingProduct(), newName, ctx.getDb());
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
