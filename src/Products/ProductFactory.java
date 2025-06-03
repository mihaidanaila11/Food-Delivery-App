package Products;

public abstract class ProductFactory {
    public abstract Product createProduct(String name, int price, String description);
}
