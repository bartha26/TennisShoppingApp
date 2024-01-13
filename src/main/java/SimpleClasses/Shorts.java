package SimpleClasses;

public class Shorts extends Products{
    private String shortsLength;
    public Shorts(Integer productId, Double price, Integer companyId, String productName, String companyName, String shortsLength) {
        super(productId, price, companyId, productName, companyName);
        this.shortsLength = shortsLength;
    }
    public String getShortsLength() {
        return shortsLength;
    }

    public void setShortsLength(String shortsLength) {
        this.shortsLength = shortsLength;
    }
}
