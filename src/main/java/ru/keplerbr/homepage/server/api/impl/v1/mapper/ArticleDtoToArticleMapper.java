package ru.keplerbr.homepage.server.api.impl.v1.mapper;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.keplerbr.homepage.data.model.Article;
import ru.keplerbr.homepage.data.model.Tag;
import ru.keplerbr.homepage.data.model.dto.ArticleDto;
import ru.keplerbr.homepage.data.model.enumerator.Language;
import ru.keplerbr.homepage.data.model.enumerator.Visibility;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ArticleDtoToArticleMapper {

  private final Map<String, BiConsumer<ArticleDto, Article>> dtoToArticleFieldMappers;

  private final Map<String, Function<ArticleDto, Object>> dtoGetters;

  private final Map<String, BiConsumer<Article, Object>> articleSetters;

  protected ArticleDtoToArticleMapper() {
    dtoToArticleFieldMappers = new HashMap<>();
    dtoToArticleFieldMappers
        .put("visibility", (dto, article) -> dto.setVisibility(article.getVisibility()));
    dtoToArticleFieldMappers
        .put("language", (dto, article) -> dto.setLanguage(article.getLanguage()));
    dtoToArticleFieldMappers.put("title", (dto, article) -> dto.setTitle(article.getTitle()));
    dtoToArticleFieldMappers
        .put("markdown", (dto, article) -> dto.setMarkdown(article.getMarkdown()));
    dtoToArticleFieldMappers
        .put("rendered", (dto, article) -> dto.setRendered(article.getRendered()));
    dtoToArticleFieldMappers
        .put("tags", (dto, article) -> dto.setTags(article.getTags()));
    dtoToArticleFieldMappers
        .put("createdAt", (dto, article) -> dto.setCreatedAt(article.getCreatedAt()));
    dtoToArticleFieldMappers
        .put("updatedAt", (dto, article) -> dto.setUpdatedAt(article.getUpdatedAt()));

    dtoGetters = new HashMap<>();
    dtoGetters.put("visibility", ArticleDto::getVisibility);
    dtoGetters.put("language", ArticleDto::getLanguage);
    dtoGetters.put("title", ArticleDto::getTitle);
    dtoGetters.put("markdown", ArticleDto::getMarkdown);
    dtoGetters.put("rendered", ArticleDto::getRendered);
    dtoGetters.put("tags", ArticleDto::getTags);
    dtoGetters.put("createdAt", ArticleDto::getCreatedAt);
    dtoGetters.put("updatedAt", ArticleDto::getUpdatedAt);

    articleSetters = new HashMap<>();
    articleSetters.put("visibility", (article, value) -> {
      if (value instanceof Visibility) {
        article.setVisibility((Visibility) value);
      }
    });
    articleSetters.put("language", (article, value) -> {
      if (value instanceof Language) {
        article.setLanguage((Language) value);
      }
    });
    articleSetters.put("title", (article, value) -> {
      if (value instanceof String) {
        article.setTitle((String) value);
      }
    });
    articleSetters.put("markdown", (article, value) -> {
      if (value instanceof String) {
        article.setMarkdown((String) value);
      }
    });
    articleSetters.put("rendered", (article, value) -> {
      if (value instanceof String) {
        article.setRendered((String) value);
      }
    });
    articleSetters.put("tags", (article, value) -> {
      if (value instanceof Set) {
        article.setTags((Set<Tag>) value);
      }
    });
    articleSetters.put("createdAt", (article, value) -> {
      if (value instanceof Date) {
        article.setCreatedAt((Date) value);
      }
    });
    articleSetters.put("updatedAt", (article, value) -> {
      if (value instanceof Date) {
        article.setUpdatedAt((Date) value);
      }
    });
    articleSetters.put("url", (article, value) -> {
      if (value instanceof String) {
        article.setUrl((String) value);
      }
    });
  }

  @Mapping(target = "visibility", source = "articleDto.visibility")
  @Mapping(target = "title", source = "articleDto.title")
  @Mapping(target = "markdown", source = "articleDto.markdown")
  @Mapping(target = "language", source = "articleDto.language")
  @Mapping(target = "tags", source = "articleDto.tags")
  public abstract Article dtoToArticle(ArticleDto articleDto);

  @Mapping(target = "visibility", source = "article.visibility")
  @Mapping(target = "language", source = "article.language")
  @Mapping(target = "title", source = "article.title")
  @Mapping(target = "markdown", source = "article.markdown")
  @Mapping(target = "rendered", source = "article.rendered")
  @Mapping(target = "tags", source = "article.tags")
  @Mapping(target = "createdAt", source = "article.createdAt")
  @Mapping(target = "updatedAt", source = "article.updatedAt")
  public abstract ArticleDto articleToDto(Article article);

  public Article patchArticleWithDto(Article article, ArticleDto articleDto) {
    dtoGetters.forEach((fieldName, getter) -> {
      if (Objects.nonNull(getter.apply(articleDto))) {
        articleSetters.get(fieldName).accept(article, getter.apply(articleDto));
      }
    });

    return article;
  }

  public ArticleDto articleToDto(Article article, Set<String> includeFields) {
    var articleDto = new ArticleDto();

    if (Objects.isNull(includeFields)) {
      return articleToDto(article);
    }
    includeFields.forEach(fieldName -> {
      if (dtoToArticleFieldMappers.containsKey(fieldName)) {
        dtoToArticleFieldMappers.get(fieldName).accept(articleDto, article);
      }
    });

    return articleDto;
  }

}
