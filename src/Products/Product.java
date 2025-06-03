package Products;

import java.util.UUID;

public class Product {
    private UUID id;
    private String name;
    private float price;
    private String description;

    public Product(String name, float price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;

        this.id = UUID.randomUUID();
    }

    public Product(String name, float price, String description, UUID id) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
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
}
