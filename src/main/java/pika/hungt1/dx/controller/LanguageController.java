package pika.hungt1.dx.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pika.hungt1.dx.models.Language;
import pika.hungt1.dx.repository.LanguageRepository;

import java.util.List;

@RestController
@RequestMapping("/api/languages")
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class LanguageController {
    private final LanguageRepository languageRepository;

    @GetMapping
    public List<Language> getAllLanguages() {
        return languageRepository.findAll();
    }

    @GetMapping("/{id}")
    public Language getLanguageById(@PathVariable String id) {
        return languageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy language bởi id " + id));
    }

    // Usage: GET /api/languages/find?name=English
    @GetMapping("/name")
    public Language getLanguageByName(@PathVariable String name) {
        return languageRepository.findByLanguage(name)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy language bởi name " + name));
    }

    @PostMapping
    public void createLanguage(@RequestBody Language language) {
        languageRepository.save(language);
    }

    @PutMapping("/{id}")
    public Language updateLanguage(@PathVariable String id, @RequestBody Language language) {
        Language old = languageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy language bởi id " + id));
        old.setLanguage(language.getLanguage());
        return languageRepository.save(old);
    }

    @DeleteMapping("/{id}")
    public String deleteLanguage(@PathVariable String id) {
        languageRepository.deleteById(id);
        return "Deleted language by id " + id;
    }
}
