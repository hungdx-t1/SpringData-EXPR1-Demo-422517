package pika.hungt1.dx.repository;

import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import pika.hungt1.dx.models.ProductCategoryTranslation;
import pika.hungt1.dx.archieve.ProductCategoryTranslationId;

@NullMarked
public interface ProductCategoryTranslationRepository
        extends JpaRepository<ProductCategoryTranslation, ProductCategoryTranslationId> {
}
