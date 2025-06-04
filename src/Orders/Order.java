package Orders;

import Products.Product;
import Stores.Restaurant;
import Users.Client;
import Users.Courier;
import Users.UserCart;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Order {
    UUID id;

    private Client client;
    private Courier courier;
    private boolean delivered;
    private UserCart cart;

    public Order(UserCart cart, Client client, Courier courier) {
        this.cart = cart;
        this.client = client;
        this.courier = courier;
        this.delivered = false;

        this.id = UUID.randomUUID();
    }

    public UUID getID() {
        return id;
    }

    public UserCart getCart() {
        return cart;
    }

    public Client getClient() {
        return client;
    }

    public Courier getCourier() {
        return courier;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setCart(UserCart cart) {
        this.cart = new UserCart();
        for (Product product : cart.getProducts()) {
            this.cart.addToCart(product, cart.getQuantity(product));
        }
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public void show() {
        System.out.println("Courier: " + (courier != null ? courier.getFirstName() : "Not assigned"));
        System.out.println("Products in Order:");
        for (Product product : cart.getProducts()) {
            System.out.println("- " + product.getName() + " (x" + cart.getQuantity(product) + ")");
        }
    }
}
