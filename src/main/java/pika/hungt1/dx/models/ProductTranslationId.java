package pika.hungt1.dx.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductTranslationId implements Serializable {
    private Integer productId;
    private String languageId;
}