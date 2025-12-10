package pika.hungt1.dx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pika.hungt1.dx.dto.CategoryDTO;
import pika.hungt1.dx.dto.ProductCreateDTO;
import pika.hungt1.dx.dto.ProductDTO;
import pika.hungt1.dx.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api") // Sửa lại root path một chút cho gọn
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired private ProductService productService;

    // API lấy danh mục: /api/categories?lang=VI
    @GetMapping("/categories")
    public List<CategoryDTO> getCategories(@RequestParam(defaultValue = "EN") String lang) {
        return productService.getAllCategories(lang);
    }

    // API lấy sản phẩm (có thêm filter): /api/products?lang=VI&cat=1
    @GetMapping("/products")
    public List<ProductDTO> getProducts(
            @RequestParam(defaultValue = "EN") String lang,
            @RequestParam(required = false) Integer cat // Thêm tham số này
    ) {
        return productService.getAllProducts(lang, cat);
    }

    @PostMapping("/products")
    public void createProduct(@RequestBody ProductCreateDTO dto) {
        productService.createProduct(dto);
    }
}