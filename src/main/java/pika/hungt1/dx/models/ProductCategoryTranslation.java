package pika.hungt1.dx.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ProductCategoryTranslation")
@Data
public class ProductCategoryTranslation {

    @EmbeddedId
    private CategoryTranslationId id;

    @ManyToOne
    @MapsId("productCategoryId") // Map với field trong ID class
    @JoinColumn(name = "ProductCategoryID")
    private ProductCategory productCategory;

    @ManyToOne
    @MapsId("languageId") // Map với field trong ID class
    @JoinColumn(name = "LanguageID")
    private Language language;

    @Column(name = "CategoryName")
    private String categoryName;
}