package pika.hungt1.dx.repository;

import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import pika.hungt1.dx.models.ProductCategory;

@NullMarked
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
}
