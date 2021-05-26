package ru.keplerbr.homepage.graphql.data.model.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import ru.keplerbr.homepage.graphql.data.model.Article;

public class ArticleSpecification {

  public static Specification<Article> byUrl(String url) {
    return (root, query, builder) -> builder.equal(root.get("url"), url);
  }

}
