package Menu.Options.Courier;

import Auth.AppContext;
import Exceptions.OrderExceptions.NoOrderAvailable;
import Menu.MenuOption;
import Orders.Order;
import Orders.OrderOperations;

import java.sql.SQLException;

public class GetOrder extends MenuOption {
    public GetOrder() {
        this.setLabel("Get Order");
    }
    @Override
    public void action(AppContext ctx) {
        if(ctx.getAuth().getLoggedCourier().getActiveOrder() != null) {
            System.out.println("You already have an active order.");
            return;
        }

        try{
            Order availableOrder = OrderOperations.getAvailableOrder(ctx);
            ctx.getAuth().getLoggedCourier().setActiveOrder(availableOrder);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (NoOrderAvailable e){
            System.out.println("No orders available at the moment.");
            return;
        } catch (Exception e) {
            System.out.println("An error occurred while fetching the order: " + e.getMessage());
            return;
        }

    }
}
