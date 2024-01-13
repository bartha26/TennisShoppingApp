package SimpleClasses;

public class ProductsOrder {
    private int orderId;
    private int productId;
    private int quantity;
    private int colorId;

    public ProductsOrder(int orderId, int productId, int quantity, int colorId) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.colorId = colorId;
    }

    // Getter methods (you can also add setters if needed)
    public int getOrderId() {
        return orderId;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getColorId() {
        return colorId;
    }
}
