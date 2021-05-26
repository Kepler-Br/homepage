package ru.keplerbr.homepage.graphql.data.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.keplerbr.homepage.graphql.data.model.Article;
import ru.keplerbr.homepage.graphql.data.model.enumerator.Language;
import ru.keplerbr.homepage.graphql.data.projection.IdProjection;

public interface ArticleRepository extends JpaRepository<Article, Long> {

  long deleteByUrl(String url);

  Optional<Article> getByUrl(String url);

  Optional<Article> findByUrl(String url);

  List<Article> findAllByLanguage(Language language);

  Long countByLanguage(Language language);

  List<Article> findAll(Specification<Article> specification);

  IdProjection getIdByUrl(String url);

  @Query("select a from Article a")
  Stream<Article> findAllAsStream(Pageable pageable);

  List<Article> findAllByIdGreaterThan(Long id, Pageable pageable);

  List<Article> findAllByIdLessThan(Long id, Pageable pageable);

  List<Article> findAllByIdLessThanOrderByIdDesc(Long id, Pageable pageable);

}
