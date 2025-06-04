package Menu.Options.Cart;

import Auth.AppContext;
import Location.Location;
import Menu.MenuOption;
import Orders.Order;
import Orders.OrderCreator;
import Orders.OrderOperations;

import java.sql.SQLException;

public class PlaceOrder extends MenuOption {
    public PlaceOrder() {
        this.setLabel("Place Order");
    }

    @Override
    public void action(AppContext ctx) {
        System.out.println("Are you using your saved address? (Y/N)");

        String choice = Menu.Menu.getStringInput().toUpperCase();

        while (!choice.equals("Y") && !choice.equals("N")) {
            System.out.println("Invalid choice. Please enter 'Y' or 'N'.");
            choice = Menu.Menu.getStringInput().toUpperCase();
        }

        Location orderLocation = ctx.getAuth().getLoggedClient().getLocation();
        if(choice.equals("N")) {
            System.out.println("Please enter your address:");
            try {
                orderLocation = Menu.Utils.LocationUtils.askForLocation(ctx);
            } catch (SQLException e){
                System.out.println(e.getMessage());
                return;
            }

        }

        System.out.println("Are you sure you want to place the order? (Y/N)");

        choice = Menu.Menu.getStringInput().toUpperCase();

        while (!choice.equals("Y") && !choice.equals("N")) {
            System.out.println("Invalid choice. Please enter 'Y' or 'N'.");
            choice = Menu.Menu.getStringInput().toUpperCase();
        }

        if(choice.equals("N")) {
            System.out.println("Order cancelled.");
            return;
        }

        try{
            OrderOperations.placeOrder(ctx, orderLocation);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }
}
