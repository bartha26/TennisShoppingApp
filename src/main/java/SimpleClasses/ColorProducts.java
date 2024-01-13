package SimpleClasses;

import javafx.scene.paint.Color;

public class ColorProducts {
    private Integer productId;
    private String color;
    public ColorProducts(Integer productId, String color)
    {
        this.productId = productId;
        this.color = color;
    }
    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer product_id) {
        this.productId = product_id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}

