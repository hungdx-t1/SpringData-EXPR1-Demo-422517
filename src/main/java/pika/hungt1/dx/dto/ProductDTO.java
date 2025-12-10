package pika.hungt1.dx.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductDTO {
    private Integer id;
    private BigDecimal price;
    private BigDecimal weight;
    private String name;        // Đã được lọc theo ngôn ngữ user chọn
    private String description; // Đã được lọc theo ngôn ngữ user chọn
    private String categoryName;
}
