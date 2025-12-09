package pika.hungt1.dx.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "ProductTranslation")
@Data
@ToString(exclude = "product") // tránh vòng lặp vô hạn khi in log
public class ProductTranslation {

    @EmbeddedId
    private ProductTranslationId id;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "ProductID")
    private Product product;

    @ManyToOne
    @MapsId("languageId")
    @JoinColumn(name = "LanguageID")
    private Language language;

    @Column(name = "ProductName")
    private String productName;

    @Column(name = "ProductDescription")
    private String productDescription;
}