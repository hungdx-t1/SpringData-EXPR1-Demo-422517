package pika.hungt1.dx.components;

import org.jspecify.annotations.NullMarked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pika.hungt1.dx.models.Product;
import pika.hungt1.dx.repository.ProductRepository;

import java.util.List;

@Component
@NullMarked
@SuppressWarnings({"NotNullFieldNotInitialized", "RedundantThrows", "unused"})
public class DemoRunner implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional // Để giữ session khi get Lazy loading
    public void run(String... args) throws Exception {
        System.out.println("====== START DEMO SPRING DATA JPA ======");

        // 1. Lấy tất cả sản phẩm
        List<Product> products = productRepository.findAll();

        for (Product p : products) {
            System.out.println("Product ID: " + p.getId() + " | Price: $" + p.getPrice());

            // In ra tên theo các ngôn ngữ
            p.getTranslations().forEach(trans ->
                    System.out.println("   - [" + trans.getLanguage().getLanguageId() + "] Name: " + trans.getProductName())
            );
        }

        // 2. Test Custom Query (Tìm 'Chuột')
        System.out.println("\n--- Tìm kiếm 'Chuột' trong tiếng Việt ---");
        productRepository.findByVietnameseName("Chuột").forEach(p ->
                System.out.println("Found: ID " + p.getId() + " (Price: " + p.getPrice() + ")")
        );
    }
}
