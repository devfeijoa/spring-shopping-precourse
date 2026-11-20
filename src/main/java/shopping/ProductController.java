package shopping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ProductController {
    List<Product> products = new ArrayList<>();
    private final AtomicLong nextProductId = new AtomicLong(1);

    @GetMapping("/api/products")
    public List<Product> getProducts() {
        return products;
    }
    @PostMapping("/api/products")
    public List<Product> createProducts(@RequestBody CreateProductRequest request) {
        long productId = nextProductId.getAndIncrement();
        Product product = new Product(request.getProductName(), productId, request.getPrice(), request.getImageUrl());
        products.add(product);
        return products;
    }
}
