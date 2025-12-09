package pika.hungt1.dx.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "Product")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductID")
    private Integer id;

    private BigDecimal price;
    private BigDecimal weight;

    @ManyToOne
    @JoinColumn(name = "ProductCategoryID")
    private ProductCategory category;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private List<ProductTranslation> translations;
}