package ru.keplerbr.homepage.data.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.keplerbr.homepage.data.model.Article;
import ru.keplerbr.homepage.data.model.enumerator.Language;

public interface ArticleRepository extends JpaRepository<Article, Long> {

  Optional<Article> findByUrl(String slug);

  List<Article> findAllByLanguage(Language language);

}
