package Orders;

import Exceptions.CartExceptions.EmptyCart;
import Exceptions.UserExceptions.LocationNotSet;

import Location.Location;
import Stores.Restaurant;
import Users.Client;

public class OrderCreator extends OrderFactory{
    @Override
    public Order createOrder(Client client, Location orderLocation) {
        if(orderLocation == null){
            throw new LocationNotSet();
        }

        if (client.getCart() == null
                || client.getCart().getProducts() == null
                || client.getCart().getProducts().isEmpty()) {
            throw new EmptyCart();
        }

        return new Order(
                client.getCart(),
                client,
                null
        );
    }
}
