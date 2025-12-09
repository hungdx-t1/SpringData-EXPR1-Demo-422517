package pika.hungt1.dx.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    private BigDecimal price;

    private BigDecimal weight;

    @ManyToOne
    @JoinColumn(name = "productCategoryId")
    private ProductCategory category;

    // ?
}