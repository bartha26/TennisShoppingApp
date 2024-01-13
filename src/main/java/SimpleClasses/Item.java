package SimpleClasses;

public class Item {
    private String productName;
    //  private Double totalPrice; // Updated to a single value
    private Double price;
    private Integer productId;
    private String quantity;
    private String size;
    private String color;
    private String type;
    //  private Integer nrProducts;

    public Item() {

    }

    public void addItem(String name, Double price, Integer id, String quantity, String size, String color, String type) {
        productName = name;
        this.price = price;
        this.productId = id;
        this.quantity = quantity;
        this.size = size;
        this.color = color;
        this.type = type;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
