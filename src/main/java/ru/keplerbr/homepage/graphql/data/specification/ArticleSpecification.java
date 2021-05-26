package ru.keplerbr.homepage.graphql.data.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.keplerbr.homepage.graphql.data.model.Article;
import ru.keplerbr.homepage.graphql.data.model.enumerator.Visibility;

public class ArticleSpecification {

  private ArticleSpecification() {
  }

  public static Specification<Article> idLessThan(long id) {
    return (root, query, builder) -> builder.lessThan(root.get("id"), id);
  }

  public static Specification<Article> idGreaterThan(long id) {
    return (root, query, builder) -> builder.greaterThan(root.get("id"), id);
  }

  public static Specification<Article> visibilityEquals(Visibility visibility) {
    return (root, query, builder) -> builder.equal(root.get("id"), visibility);
  }

}
