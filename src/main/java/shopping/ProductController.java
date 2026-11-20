package shopping;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ProductController {
    List<Product> products = new ArrayList<>();
    private final AtomicLong nextProductId = new AtomicLong(1);
    private final PurgoMalumClient purgoMalumClient;

    @GetMapping("/api/products")
    public List<Product> getProducts() {
        return products;
    }

    @PostMapping("/api/products")
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProducts(@Valid @RequestBody CreateProductRequest request) {
        long productId = nextProductId.getAndIncrement();
        purgoMalumClient.validate(request.getProductName());
        Product product = new Product(request.getProductName(), productId, request.getPrice(), request.getImageUrl());
        products.add(product);
        return product;
    }

    @GetMapping("/api/products/{productId}")
    public Product getProduct(@PathVariable("productId") Long productId) {
        return products.get(findProductIndex(productId));
    }

    @PutMapping("/api/products/{productId}")
    public Product updateProduct(
            @PathVariable("productId") Long productId,
            @Valid @RequestBody CreateProductRequest request) {
        int index = findProductIndex(productId);
        purgoMalumClient.validate(request.getProductName());
        Product updated = new Product(request.getProductName(), productId, request.getPrice(), request.getImageUrl());
        products.set(index, updated);
        return updated;
    }

    private int findProductIndex(Long productId) {
        int index = 0;
        for (Product product : products) {
            if (product.getProductId().equals(productId)) {
                return index;
            }
            index++;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
    }

    @DeleteMapping("/api/products/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable("productId") Long productId) {
        int index = findProductIndex(productId);
        products.remove(index);
    }

    public ProductController(PurgoMalumClient purgoMalumClient) {
        this.purgoMalumClient = purgoMalumClient;
    }
}
