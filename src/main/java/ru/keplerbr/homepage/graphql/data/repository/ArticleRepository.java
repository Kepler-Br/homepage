package ru.keplerbr.homepage.graphql.data.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.keplerbr.homepage.graphql.data.model.Article;
import ru.keplerbr.homepage.graphql.data.model.enumerator.Language;
import ru.keplerbr.homepage.graphql.data.projection.IdProjection;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {

  long deleteByUrl(String url);

  Optional<Article> getByUrl(String url);

  Optional<Article> findByUrl(String url);

  List<Article> findAllByLanguage(Language language);

  Long countByLanguage(Language language);

  List<Article> findAll(Specification<Article> specification);

  List<Article> findAll(Specification<Article> specification, Pageable pageable);

  IdProjection getIdByUrl(String url);

  List<Article> findAllByIdGreaterThan(Long id, Pageable pageable);

  List<Article> findAllByIdLessThan(Long id, Pageable pageable);

  List<Article> findAllByIdLessThanOrderByIdDesc(Long id, Pageable pageable);
}
