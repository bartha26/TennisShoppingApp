package SimpleClasses;

public class Review {
    private Integer reviewId;
    private Integer rating;
    private String userName;
    private Integer userId;
    private String comments;
    private String productName;
    private Integer productId;

    public String getUserName() {
        return userName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Review(Integer rating, String userName, String comments, String productName, Integer productId, Integer userId)
    {
        this.comments = comments;
        this.productName = productName;
        this.rating = rating;
        this.userName = userName;
        this.productId = productId;
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getProductId() {
        return productId;
    }
    public void setProductId(Integer product_id) {
        this.productId = product_id;
    }
}