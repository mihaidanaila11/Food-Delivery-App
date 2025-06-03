package Products;

public class ProductCreator extends ProductFactory{
    @Override
    public Product createProduct(String name, int price, String description) {
        if(name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }
        if(price <= 0) {
            throw new IllegalArgumentException("Product price must be greater than zero");
        }

        return new Product(name, price, description);
    }
}
