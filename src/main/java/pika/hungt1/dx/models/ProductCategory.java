package pika.hungt1.dx.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "ProductCategory")
@Data
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductCategoryID")
    private Integer id;

    @Column(name = "CanBeShipped")
    private Boolean canBeShipped;

    // Quan hệ 1-N với bản dịch
    // Optional: để fetch data dễ hơn
    @OneToMany(mappedBy = "productCategory", fetch = FetchType.LAZY)
    private List<ProductCategoryTranslation> translations;
}
