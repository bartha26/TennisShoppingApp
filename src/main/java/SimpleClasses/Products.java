package SimpleClasses;

public class Products extends Company {
    private Integer productId;
    private Double price;
    private String productName;
    Products(Integer productId, Double price, Integer companyId, String productName, String companyName)
    {
        super(companyId,companyName);
        this.productId = productId;
        this.productName = productName;
        this.price = price;
    }
    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

}
