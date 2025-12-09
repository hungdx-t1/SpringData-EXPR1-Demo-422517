package pika.hungt1.dx.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pika.hungt1.dx.models.Product;
import pika.hungt1.dx.models.ProductCategory;
import pika.hungt1.dx.repository.ProductCategoryRepository;
import pika.hungt1.dx.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;

    // Phương thức GET (READ) - all
    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Phương thức GET - theo ID
    // Usage: "/api/products/{id}"
    @GetMapping("/{id}")
    public Product getProductsById(@PathVariable Integer id) {
        return productRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Không tìm thấy product bởi id " + id)
        );
    }

    @GetMapping("/category/{id}")
    public List<Product> getProductsByCategory(@PathVariable Integer id) {
        Optional<ProductCategory> categoryOptional = productCategoryRepository.findById(id);
        ProductCategory category;
//        if(categoryOptional.isEmpty()) {
//            category = null;
//        } else {
//            category = categoryOptional.get();
//        }
        category = categoryOptional.orElse(null);

        if(category == null) {
            throw new RuntimeException("Không tìm thấy danh mục bởi id " + id);
        }

        return productRepository.getProductsByCategory(category);
    }

    // Phương thức POST (CREATE)
    @PostMapping
    public void createProduct(@RequestBody Product product) {
        productRepository.save(product);
    }

    // Phương thức PUT (UPDATE)
    @PutMapping("{/id}")
    public Product updateProduct(@PathVariable Integer id, @RequestBody Product updated) {
        // Lấy product bởi id
        Product p = productRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Không tìm thấy product bởi id " + id)
        );
        p.setPrice(updated.getPrice());
        p.setWeight(updated.getWeight());
        p.setCategory(updated.getCategory());
        return productRepository.save(p);
    }

    // Phương thức DELETE
    @DeleteMapping("{/id}")
    public String deleteProduct(@PathVariable Integer id) {
        productRepository.deleteById(id);
        return "Deleted product with id " + id;
    }
}
