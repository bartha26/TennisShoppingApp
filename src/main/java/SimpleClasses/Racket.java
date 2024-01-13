package SimpleClasses;

public class Racket extends Products{
    private Integer headSize;
    public Racket(Integer productId, Double price, Integer companyId, String productName, String companyName, Integer headSize)
    {
        super(productId,price,companyId,productName,companyName);
        this.headSize = headSize;
    }
    public Integer getHeadSize() {
        return headSize;
    }

    public void setHeadSize(Integer headSize) {
        this.headSize = headSize;
    }

}
