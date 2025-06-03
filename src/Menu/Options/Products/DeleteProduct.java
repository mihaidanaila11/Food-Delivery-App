package Menu.Options.Products;

import Auth.AppContext;
import Menu.MenuOption;
import Products.ProductOperations;
import Stores.RestaurantOperations;

import java.sql.SQLException;

public class DeleteProduct extends MenuOption {
    public DeleteProduct() {
        this.setLabel("Delete Product");
    }
    @Override
    public void action(AppContext ctx) {
        try{
            ProductOperations.delete(ctx.getEditingProduct(), ctx.getDb(), ctx);
        } catch (SQLException e){
            System.out.println(e.getMessage());
            return;
        }

        ctx.setEditingProduct(null);
    }
}
