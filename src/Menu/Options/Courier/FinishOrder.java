package Menu.Options.Courier;

import Auth.AppContext;
import Menu.MenuOption;
import Orders.OrderOperations;

import java.sql.SQLException;

public class FinishOrder extends MenuOption {
    public FinishOrder() {
        this.setLabel("Finish Order");
    }

    @Override
    public void action(AppContext ctx) {
        try{
            OrderOperations.finishOrder(ctx, ctx.getAuth().getLoggedCourier().getActiveOrder());
            ctx.getAuth().getLoggedCourier().setActiveOrder(null);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }
}
