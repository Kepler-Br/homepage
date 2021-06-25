package ru.keplerbr.homepage.graphql.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.keplerbr.homepage.graphql.data.model.ArticleLanguage;

import java.util.List;
import java.util.Set;

public interface ArticleLanguageRepository extends JpaRepository<ArticleLanguage, Long> {

    List<ArticleLanguage> findAllByNameIn(Set<String> name);
}
