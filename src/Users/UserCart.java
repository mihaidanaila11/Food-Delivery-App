package Users;

import Products.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class UserCart {
    private final HashMap<Product, Integer> cart;

    public UserCart() {
        this.cart = new HashMap<>();
    }

    public void addToCart(Product product, int quantity) {
        cart.put(product, cart.getOrDefault(product, 0) + quantity);
    }

    public void removeFromCart(Product product) {
        cart.remove(product);
    }

    public Set<Product> getProducts() {
        return cart.keySet();
    }

    public int getQuantity(Product product) {
        return cart.getOrDefault(product, 0);
    }

    public boolean isEmpty() {
        return cart.isEmpty();
    }
}
