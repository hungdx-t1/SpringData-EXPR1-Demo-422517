package pika.hungt1.dx.repository;

import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pika.hungt1.dx.models.Product;
import pika.hungt1.dx.models.ProductCategory;

import java.util.List;

@NullMarked
public interface ProductRepository extends JpaRepository<Product, Integer> {
    // Tự động tạo query: SELECT * FROM Product WHERE ProductCategoryID = ?
    List<Product> findByCategoryId(Integer categoryId);

    // Ví dụ Custom Query: Tìm sản phẩm có tên tiếng Việt chứa từ khóa
    @Query("SELECT p FROM Product p " +
            "JOIN p.translations t " +
            "WHERE t.language.id = 'VI' AND t.productName LIKE %:keyword%")
    List<Product> findByVietnameseName(String keyword);
}
