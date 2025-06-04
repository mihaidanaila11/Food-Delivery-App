package Orders;

import Auth.AppContext;
import Location.Location;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class OrderOperations {
    public static void placeOrder(AppContext ctx, Location orderLocation) throws SQLException {
        OrderCreator orderCreator = new OrderCreator();

        try{
            Order order = orderCreator.createOrder(
                    ctx.getAuth().getLoggedClient(),
                    orderLocation
            );

            order.setCart(ctx.getAuth().getLoggedClient().getCart());

            ctx.getDb().insertOrder(order);

            ResultSet availableCouriers = ctx.getDb().getAvailableCouriers(ctx.getAuth().getLoggedClient().getLocation());
            if(availableCouriers.next()) {
                String courierId = availableCouriers.getString("CourierID");
                ctx.getDb().assignCourierToOrder(order.getID(), UUID.fromString(courierId));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return;
        }

        ctx.getAuth().getLoggedClient().getCart().emptyCart();
    }
}
