package shopping;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProducts(@RequestBody CreateProductRequest request) {
        long productId = nextProductId.getAndIncrement();
        Product product = new Product(request.getProductName(), productId, request.getPrice(), request.getImageUrl());
        products.add(product);
        return product;
    }

    @GetMapping("/api/products/{productId}")
    public Product getProduct(@PathVariable("productId") Long productId) {
        for (Product product : products) {
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null;
    }
}
