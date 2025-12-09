package pika.hungt1.dx.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ProductTranslationId.class)
public class ProductTranslation {
    @Id
    private Integer productId;

    @Id
    @Column(length = 2)
    private String languageId;

    private String productName;

    private String productDescription;

    @ManyToOne
    @JoinColumn(name = "productId", insertable = false, updatable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "languageId", insertable = false, updatable = false)
    private Language language;
}