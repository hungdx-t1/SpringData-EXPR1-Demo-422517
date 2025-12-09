package pika.hungt1.dx.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Language")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Language {
    @Id
    @Column(name = "LanguageID", length = 2)
    private String languageId; // 'EN', 'VI'

    @Column(name = "Language", nullable = false, length = 20)
    private String language;
}
