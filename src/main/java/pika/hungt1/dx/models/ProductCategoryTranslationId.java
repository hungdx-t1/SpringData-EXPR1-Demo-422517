package pika.hungt1.dx.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryTranslationId implements Serializable {
    private Integer productCategoryId;
    private String languageId;
}