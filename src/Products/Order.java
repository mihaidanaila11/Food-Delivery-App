package Products;

import Stores.Restaurant;
import Users.Client;
import Users.Courier;

import java.util.List;

public class Order {
    private List<Product> products;
    private Client client;
    private Courier courier;
    private Restaurant restaurant;

    public Order(List<Product> products, Client client, Courier courier, Restaurant restaurant) {
        this.products = products;
        this.client = client;
        this.courier = courier;
        this.restaurant = restaurant;
    }

    public List<Product> getProducts() {
        return products;
    }

    public Client getClient() {
        return client;
    }

    public Courier getCourier() {
        return courier;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
