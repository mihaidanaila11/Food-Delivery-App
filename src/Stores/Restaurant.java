package Stores;

import Location.Address;
import Products.Product;

import java.util.List;

public class Restaurant {
    private String name;
    private Address address;
    private List<Product> products;

    public Restaurant(String name, Address address, List<Product> products) {
        this.name = name;
        this.address = address;
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
