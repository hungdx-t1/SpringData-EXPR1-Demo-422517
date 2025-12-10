package pika.hungt1.dx.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductCreateDTO {
    private BigDecimal price;
    private Integer categoryId;
    private String nameEN;
    private String descriptionEN;
    private String nameVI;
    private String descriptionVI;
}
