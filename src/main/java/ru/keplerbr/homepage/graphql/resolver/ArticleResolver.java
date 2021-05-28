package ru.keplerbr.homepage.graphql.resolver;

import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.relay.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.keplerbr.homepage.graphql.data.model.Article;
import ru.keplerbr.homepage.graphql.data.model.Tag;
import ru.keplerbr.homepage.graphql.data.model.enumerator.Visibility;
import ru.keplerbr.homepage.graphql.data.model.exception.GraphQLIllegalArgumentException;
import ru.keplerbr.homepage.graphql.data.model.exception.GraphQLNotFoundException;
import ru.keplerbr.homepage.graphql.data.repository.ArticleRepository;
import ru.keplerbr.homepage.graphql.data.specification.ArticleSpecification;
import ru.keplerbr.homepage.graphql.data.utils.Base64Utils;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class ArticleResolver implements GraphQLQueryResolver {

  final ArticleRepository articleRepository;

  @Value("${api.max-page-count.article}")
  long maxArticlePageCount;

  public Tag tag(String name) {
    return new Tag(name);
  }

  private static ConnectionCursor getConnectionCursor(Article article) {
    byte[] articleIdBytes = article.getId().toString().getBytes(StandardCharsets.UTF_8);
    String base64String = Base64.getEncoder().encodeToString(articleIdBytes);

    return new DefaultConnectionCursor(base64String);
  }

  private static Connection<Article> getEmptyConnection() {
    PageInfo pageInfo = new DefaultPageInfo(null, null, false, false);

    return new DefaultConnection<>(new ArrayList<>(), pageInfo);
  }

  private static long validateArguments(long count, @Nullable String cursor) {
    if (count <= 0L) {
      throw new GraphQLIllegalArgumentException("Count(\"first\"/\"last\") cannot be <= 0");
    }

    long articleId;

    if (Objects.isNull(cursor)) {
      articleId = 0L;
    } else {
      try {
        articleId = Base64Utils.parseLong(cursor);
      } catch (NumberFormatException ex) {
        throw new NoSuchElementException("Cursor is invalid");
      }
    }

    return articleId;
  }

  private Connection<Article> constructConnection(
      List<Article> articleList, boolean hasNext, boolean hasPrevious, long limit) {
    List<Edge<Article>> edges =
        articleList.stream()
            .limit(limit)
            .map(article -> new DefaultEdge<>(article, getConnectionCursor(article)))
            .collect(Collectors.toList());
    PageInfo pageInfo =
        new DefaultPageInfo(
            edges.isEmpty() ? null : edges.get(0).getCursor(),
            edges.isEmpty() ? null : edges.get(edges.size() - 1).getCursor(),
            hasPrevious,
            hasNext);

    return new DefaultConnection<>(edges, pageInfo);
  }

  @Transactional(readOnly = true)
  public Connection<Article> articlesBackward(
      long last, @Nullable String before, @Nullable Visibility filterByVisibility) {
    long articleId = validateArguments(last, before);

    articleId = articleId == 0L ? Long.MAX_VALUE : articleId;

    if (articleRepository.count() == 0L) {
      return getEmptyConnection();
    }

    last = (last > maxArticlePageCount) ? maxArticlePageCount : last;

    List<Article> fetchedArticles =
        articleRepository.findAllByIdLessThanOrderByIdDesc(
            articleId, PageRequest.of(0, (int) last + 1));
    long fetchedArticleCount = fetchedArticles.size();
    boolean hasNext = fetchedArticleCount > last;
    boolean hasPrevious =
        !articleRepository.findAllByIdGreaterThan(articleId, PageRequest.of(0, 1)).isEmpty();

    return constructConnection(fetchedArticles, hasNext, hasPrevious, last);
  }

  @Transactional(readOnly = true)
  public Connection<Article> articlesForward(
      long first, @Nullable String after, @Nullable Visibility filterByVisibility) {
    long articleId = validateArguments(first, after);

    if (articleRepository.count() == 0L) {
      return getEmptyConnection();
    }

    first = (first > maxArticlePageCount) ? maxArticlePageCount : first;

    Specification<Article> specification = ArticleSpecification.idGreaterThan(articleId);

    if (Objects.nonNull(filterByVisibility)) {
      specification = specification.and(ArticleSpecification.visibilityEquals(filterByVisibility));
    }

    Pageable pageable = PageRequest.of(0, (int) first + 1);
    List<Article> fetchedArticles = articleRepository.findAll(specification, pageable);
    long fetchedArticleCount = fetchedArticles.size();
    boolean hasNext = fetchedArticleCount > first;
    boolean hasPrevious =
        !articleRepository.findAllByIdLessThan(articleId, PageRequest.of(0, 1)).isEmpty();

    return constructConnection(fetchedArticles, hasNext, hasPrevious, first);
  }

  @Transactional(readOnly = true)
  public Article article(String url) {
    return articleRepository
        .getByUrl(url)
        .orElseThrow(
            () ->
                new GraphQLNotFoundException(
                    String.format("Article with url \"%s\" was not found", url)));
  }
}
