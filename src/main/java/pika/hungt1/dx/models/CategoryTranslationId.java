package pika.hungt1.dx.models;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@EqualsAndHashCode
public class CategoryTranslationId implements Serializable {
    private Integer productCategoryId;
    private String languageId;
}
