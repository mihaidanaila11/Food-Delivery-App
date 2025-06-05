package Orders;

import Auth.AppContext;
import Exceptions.OrderExceptions.NoOrderAvailable;
import Location.Location;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import static Logger.Logger.logOperation;

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

            logOperation("Placed order for client: " + ctx.getAuth().getLoggedClient().getId().toString() +
                    " with order ID: " + order.id +
                    " at location: " + orderLocation);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return;
        }

        ctx.getAuth().getLoggedClient().getCart().emptyCart();
    }

    public static Order getAvailableOrder(AppContext ctx) throws SQLException {
        ResultSet availableOrders = ctx.getDb().getAvailableOrders(ctx.getAuth().getLoggedCourier().getWorkingCity());
        if (!availableOrders.next()) {
            throw new NoOrderAvailable();
        }

        String orderId = availableOrders.getString("OrderID");
        Order order = ctx.getDb().fetchOrderById(UUID.fromString(orderId));

        if (order == null) {
            throw new NoOrderAvailable();
        }

        ctx.getDb().assignCourierToOrder(order.getID(), ctx.getAuth().getLoggedCourier().getId());
        System.out.println("You have accepted the order: " + order);

        logOperation("Courier " + ctx.getAuth().getLoggedCourier().getId() +
                " has accepted order with ID: " + order.getID());
        return order;
    }

    public static void finishOrder(AppContext ctx, Order activeOrder) throws SQLException{
        if (activeOrder == null) {
            System.out.println("You do not have an active order to finish.");
            return;
        }

        ctx.getDb().finishOrder(activeOrder);
        System.out.println("Order " + activeOrder.getID() + " has been finished successfully.");

        logOperation("Courier " + ctx.getAuth().getLoggedCourier().getId() +
                " has finished order with ID: " + activeOrder.getID());
    }
}
