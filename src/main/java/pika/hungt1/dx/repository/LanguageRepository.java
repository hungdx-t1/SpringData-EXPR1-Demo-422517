package pika.hungt1.dx.repository;

import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import pika.hungt1.dx.models.Language;

import java.util.Optional;

@NullMarked
public interface LanguageRepository extends JpaRepository<Language, String> {
    Optional<Language> findByLanguage(String language);
}
