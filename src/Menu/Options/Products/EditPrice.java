package Menu.Options.Products;

import Auth.AppContext;
import Menu.MenuOption;
import Products.ProductOperations;
import Users.User;

import java.sql.SQLException;

public class EditPrice extends MenuOption {
    public EditPrice() {
        this.setLabel("Edit Product Price");
        this.addNeededRole(User.Roles.OWNER);
    }
    @Override
    public void action(AppContext ctx) {
        System.out.println("Enter new product price:");
        Float newPrice = Menu.Menu.getFloatInput();

        try{
            ProductOperations.changePrice(ctx.getEditingProduct(), newPrice, ctx.getDb());
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
