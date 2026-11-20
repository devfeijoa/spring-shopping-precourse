package shopping;

public class CreateProductRequest {
    private String productName;
    private int price;
    private String imageUrl;

    public CreateProductRequest(String productName, int price, String imageUrl) {
        this.productName = productName;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getProductName() {
        return productName;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
