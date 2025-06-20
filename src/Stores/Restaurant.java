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
    private ArrayList<Product> products;
    private Owner owner;
    private String description;

    public Restaurant(String name, Location location, ArrayList<Product> products) {
        this.name = name;
        this.location = location;
        this.products = products;
    }

    public Restaurant(String name, Location location, Owner owner, String description) {
        this.name = name;
        this.location = location;
        this.owner = owner;
        this.description = description;
        this.products = new ArrayList<>();
    }

    public Restaurant(String name, Location location) {
        this.name = name;
        this.location = location;
        this.products = new ArrayList<>();
    }

    public Restaurant(String name, Location location, String description) {
        this.name = name;
        this.location = location;
        this.description = description;
    }


    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(Location location) {
        this.location = location;
    }

    public void setProducts(ArrayList<Product> products) {
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

    public String getDescription() { return description; }

    @Override
    public String toString(){ return name; }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void setDescription(String newDescription) {
        this.description = newDescription;
    }

    public void removeProduct(Product product) {
        this.products.remove(product);
    }
}
