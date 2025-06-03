package Menu.Options.Products;

import Auth.AppContext;
import Menu.MenuOption;
import Products.ProductOperations;

import java.sql.SQLException;

public class EditDescription extends MenuOption {
    public EditDescription(){
        this.setLabel("Edit Product Description");
    }

    @Override
    public void action(AppContext ctx) {
        System.out.println("Enter new product name:");
        String newDescription = Menu.Menu.getStringInput();

        try{
            ProductOperations.changeDescription(ctx.getEditingProduct(), newDescription, ctx.getDb());
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
