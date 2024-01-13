package SimpleClasses;

public class ProductSize {
    private Integer productId;
    private String size;
    private Integer quantity;
    public ProductSize(Integer shoeId, String size, Integer quantity)
    {
        this.productId = shoeId;
        this.size = size;
        this.quantity = quantity;
    }

    public Integer getShoeId() {
        return productId;
    }

    public void setShoeId(Integer shoeId) {
        this.productId = shoeId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
