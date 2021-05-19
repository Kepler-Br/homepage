package ru.keplerbr.homepage.server.api.impl.v1.mapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import ru.keplerbr.homepage.data.model.Article;
import ru.keplerbr.homepage.data.model.Tag;
import ru.keplerbr.homepage.data.model.request.ArticleAlternationRequest;
import ru.keplerbr.homepage.data.model.response.ArticleResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ArticleRequestToArticleMapper {

  @Mappings({
      @Mapping(target = "visibility", source = "request.visibility"),
      @Mapping(target = "title", source = "request.title"),
      @Mapping(target = "markdown", source = "request.body"),
      @Mapping(target = "language", source = "request.language"),
      @Mapping(target = "tags", source = "request.tags"),
  })
  public abstract Article requestToArticle(ArticleAlternationRequest request);

  @Mappings({
      @Mapping(target = "visibility", source = "article.visibility"),
      @Mapping(target = "title", source = "article.title"),
      @Mapping(target = "body", source = "article.markdown"),
      @Mapping(target = "language", source = "article.language"),
      @Mapping(target = "tags", source = "article.tags"),
  })
  public abstract ArticleAlternationRequest articleToRequest(Article article);

  @Mapping(target = "visibility", source = "article.visibility")
  @Mapping(target = "language", source = "article.language")
  @Mapping(target = "title", source = "article.title")
  @Mapping(target = "markdown", source = "article.markdown")
  @Mapping(target = "rendered", source = "article.rendered")
  @Mapping(target = "tags", source = "article.tags")
  @Mapping(target = "createdAt", source = "article.createdAt")
  @Mapping(target = "updatedAt", source = "article.updatedAt")
  public abstract ArticleResponse articleToResponse(Article article);

  public ArticleResponse articleToResponse(Article article, List<String> ignoredFields) {
    ArticleResponse response = articleToResponse(article);

    if (ignoredFields.isEmpty()) {
      return response;
    }

    Class<?> aClass = response.getClass();
    Map<String, Field> fields = Arrays.stream(aClass.getFields()).collect(Collectors.toMap(Field::getName,
        Function.identity()));
    for (var ignoredField : ignoredFields) {
      if (fields.containsKey(ignoredField)) {
//        Field field = fields.get(ignoredField);
//        field.setAccessible(true);
//        field.set(response, response);
      }
    }
  }

  Set<Tag> stringToEnumTag(Set<String> value) {
    if (Objects.isNull(value)) {
      return Collections.emptySet();
    }
    return value
        .stream()
        .map(Tag::new)
        .collect(Collectors.toSet());
  }

  Set<String> enumToStringTag(Set<Tag> value) {
    if (Objects.isNull(value)) {
      return Collections.emptySet();
    }
    return value
        .stream()
        .map(Tag::toString)
        .collect(Collectors.toSet());
  }

}
