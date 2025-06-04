package Products;

import Stores.Restaurant;

import java.util.UUID;

public class Product {
    private UUID id;
    private String name;
    private float price;
    private String description;
    private Restaurant restaurant;

    public Product(String name, float price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;

        this.id = UUID.randomUUID();
    }

    public Product(String name, float price, String description, UUID id, Restaurant restaurant) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.id = id;
        this.restaurant = restaurant;
    }

    @Override
    public String toString() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public UUID getID() { return id; }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void show() {
        System.out.println("Name: " + name);
        System.out.println("Price: " + price);
        System.out.println("Description: " + description);
        System.out.println("Restaurant: " + restaurant.getName());
    }
}
