package pika.hungt1.dx.repository;

import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import pika.hungt1.dx.models.ProductTranslation;
import pika.hungt1.dx.models.ProductTranslationId;

@NullMarked
public interface ProductTranslationRepository extends JpaRepository<ProductTranslation, ProductTranslationId> {
}
