package Orders;

import Products.Product;
import Stores.Restaurant;
import Users.Client;
import Location.Location;
public abstract class OrderFactory {
    public abstract Order createOrder(Client client, Location orderLocation);
}
