package ru.keplerbr.homepage.data.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.keplerbr.homepage.data.model.Article;
import ru.keplerbr.homepage.data.model.enumerator.Language;

public interface ArticleRepository extends JpaRepository<Article, Long> {

  Optional<Article> getByUrl(String url);

  Optional<Article> findByUrl(String url);

  List<Article> findAllByLanguage(Language language);

}
