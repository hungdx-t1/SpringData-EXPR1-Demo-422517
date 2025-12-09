package pika.hungt1.dx.archieve;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import pika.hungt1.dx.models.Language;
import pika.hungt1.dx.models.ProductCategory;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ProductCategoryTranslationId.class)
public class OldProductCategoryTranslation {
    @Id
    private Integer productCategoryId;

    @Id
    @Column(length = 2)
    private String languageId;

    private String categoryName;

    @ManyToOne
    @JoinColumn(name = "productCategoryId", insertable = false, updatable = false)
    private ProductCategory category;

    @ManyToOne
    @JoinColumn(name = "languageId", insertable = false, updatable = false)
    private Language language;
}
