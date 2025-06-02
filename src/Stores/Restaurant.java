package Stores;

import Location.Location;
import Products.Product;
import Users.Owner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Restaurant {
    private UUID ID;
    private String name;
    private Location location;
    private List<Product> products;
    private Owner owner;

    public Restaurant(String name, Location location, ArrayList<Product> products) {
        this.name = name;
        this.location = location;
        this.products = products;
    }

    public Restaurant(String name, Location location, Owner owner) {
        this.name = name;
        this.location = location;

        this.owner = owner;
    }

    public Restaurant(String name, Location location) {
        this.name = name;
        this.location = location;
    }


    public String getName() {
        return name;
    }

    public Location getLocation() {
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

    public UUID getID() {
        return ID;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setID(UUID ID) { this.ID = ID; }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
