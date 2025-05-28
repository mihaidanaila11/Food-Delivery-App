package Stores;

import Location.Location;
import Products.Product;

import java.util.List;

public class Restaurant {
    private String name;
    private Location location;
    private List<Product> products;

    public Restaurant(String name, Location location, List<Product> products) {
        this.name = name;
        this.location = location;
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public Location getAddress() {
        return location;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(Location location) {
        this.location = location;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
