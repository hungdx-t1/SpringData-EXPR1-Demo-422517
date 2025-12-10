package pika.hungt1.dx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pika.hungt1.dx.dto.CategoryDTO;
import pika.hungt1.dx.dto.ProductCreateDTO;
import pika.hungt1.dx.dto.ProductDTO;
import pika.hungt1.dx.models.*;
import pika.hungt1.dx.repository.LanguageRepository;
import pika.hungt1.dx.repository.ProductCategoryRepository;
import pika.hungt1.dx.repository.ProductRepository;
import pika.hungt1.dx.repository.ProductTranslationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryRepository categoryRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private ProductTranslationRepository translationRepository;

    public List<ProductDTO> getAllProducts(String languageCode) {
        List<Product> products = productRepository.findAll();

        return products.stream().map(product -> convertToDTO(product, languageCode))
                .collect(Collectors.toList());
    }

    public List<CategoryDTO> getAllCategories(String langCode) {
        return categoryRepository.findAll().stream().map(cat -> {
            CategoryDTO dto = new CategoryDTO();
            dto.setId(cat.getId());

            // Tìm bản dịch
            var trans = cat.getTranslations().stream()
                    .filter(t -> t.getLanguage().getLanguageId().equals(langCode))
                    .findFirst().orElse(null);

            dto.setName(trans != null ? trans.getCategoryName() : "Unknown");
            return dto;
        }).collect(Collectors.toList());
    }

    public List<ProductDTO> getAllProducts(String langCode, Integer categoryId) {
        List<Product> products;

        // Nếu có ID thì lọc, không thì lấy hết
        if (categoryId != null && categoryId > 0) {
            products = productRepository.findByCategoryId(categoryId);
        } else {
            products = productRepository.findAll();
        }

        return products.stream()
                .map(p -> convertToDTO(p, langCode)) // Hàm convertToDTO giữ nguyên như cũ
                .collect(Collectors.toList());
    }

    private ProductDTO convertToDTO(Product product, String langCode) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setPrice(product.getPrice());
        dto.setWeight(product.getWeight());

        // 1. Lấy tên SẢN PHẨM theo ngôn ngữ
        ProductTranslation prodTrans = product.getTranslations().stream()
                .filter(t -> t.getLanguage().getLanguageId().equals(langCode))
                .findFirst()
                .orElse(null);

        if (prodTrans != null) {
            dto.setName(prodTrans.getProductName());
            dto.setDescription(prodTrans.getProductDescription());
        } else {
            dto.setName("N/A");
            dto.setDescription("N/A");
        }

        // Lấy tên cate theo ngôn ngữ
        if (product.getCategory() != null) {
            ProductCategoryTranslation catTrans = product.getCategory().getTranslations().stream()
                    .filter(t -> t.getLanguage().getLanguageId().equals(langCode))
                    .findFirst()
                    .orElse(null);

            if (catTrans != null) {
                dto.setCategoryName(catTrans.getCategoryName());
            } else {
                dto.setCategoryName("Unknown Category");
            }
        }

        return dto;
    }

    public void createProduct(ProductCreateDTO dto) {
        // Lưu bảng Product
        Product product = new Product();
        product.setPrice(dto.getPrice());
        product.setWeight(new java.math.BigDecimal("1.0")); // Set cứng demo

        // Link với Category
        ProductCategory cat = categoryRepository.findById(dto.getCategoryId()).orElse(null);
        product.setCategory(cat);

        Product savedProduct = productRepository.save(product); // Có ID sau dòng này

        // Lưu bản dịch tiếng Anh (EN)
        saveTranslation(savedProduct, "EN", dto.getNameEN(), dto.getDescriptionEN());

        // Lưu bản dịch tiếng Việt (VI)
        saveTranslation(savedProduct, "VI", dto.getNameVI(), dto.getDescriptionVI());
    }

    private void saveTranslation(Product product, String langId, String name, String desc) {
        Language lang = languageRepository.findById(langId).orElse(null);
        if (lang != null) {
            ProductTranslation trans = new ProductTranslation();

            // Set ID phức hợp
            ProductTranslationId id = new ProductTranslationId();
            id.setProductId(product.getId());
            id.setLanguageId(langId);
            trans.setId(id);

            trans.setProduct(product);
            trans.setLanguage(lang);
            trans.setProductName(name);
            trans.setProductDescription(desc);

            translationRepository.save(trans);
        }
    }
}
