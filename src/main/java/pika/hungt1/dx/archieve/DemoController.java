package pika.hungt1.dx.archieve;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pika.hungt1.dx.models.*;
import pika.hungt1.dx.repository.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DemoController {
    private final LanguageRepository languageRepo;
    private final ProductCategoryRepository categoryRepo;
    private final ProductRepository productRepo;
    private final ProductTranslationRepository productTransRepo;
    private final ProductCategoryTranslationRepository categoryTransRepo;

    @GetMapping("/init")
    public String init() {
        Language en = new Language("EN", "English");
        Language vi = new Language("VI", "Vietnamese");
        languageRepo.saveAll(List.of(en, vi));

        ProductCategory cat = categoryRepo.save(new ProductCategory(null, true));
        Product p = productRepo.save(new Product(null, new BigDecimal("19.99"), new BigDecimal("0.5"), cat));

        categoryTransRepo.save(new ProductCategoryTranslation(cat.getProductCategoryId(), "EN", "Electronics", cat, en));
        categoryTransRepo.save(new ProductCategoryTranslation(cat.getProductCategoryId(), "VI", "Điện tử", cat, vi));

        productTransRepo.save(new ProductTranslation(p.getProductId(), "EN", "Wireless Mouse", "A smooth optical mouse", p, en));
        productTransRepo.save(new ProductTranslation(p.getProductId(), "VI", "Chuột không dây", "Chuột quang mượt mà", p, vi));

        return "Database initialized!";
    }

    @GetMapping("/products")
    public List<ProductTranslation> getProducts(@RequestParam String lang) {
        List<ProductTranslation> productTranslations = productTransRepo.findAll();
        List<ProductTranslation> output = new ArrayList<>();
        for (ProductTranslation pt : productTranslations) {
            if (pt.getLanguageId().equalsIgnoreCase(lang)) {
                output.add(pt);
            }
        }
        return output;
    }
}
