package SimpleClasses;

public class Order {
    private int orderId;
    private int userId;
    private double price;
    private java.sql.Date date;
    private String shippingAddress;

    public Order(int orderId, int userId, double price, java.sql.Date date, String shippingAddress) {
        this.orderId = orderId;
        this.userId = userId;
        this.price = price;
        this.date = date;
        this.shippingAddress = shippingAddress;
    }

    // Getter methods (you can also add setters if needed)
    public int getOrderId() {
        return orderId;
    }

    public int getUserId() {
        return userId;
    }

    public double getPrice() {
        return price;
    }

    public java.sql.Date getDate() {
        return date;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }
}
