package ru.keplerbr.homepage.server.api.impl.v1.mapper;

import java.util.Objects;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.keplerbr.homepage.data.model.Article;
import ru.keplerbr.homepage.data.model.Tag;
import ru.keplerbr.homepage.data.model.dto.ArticleDto;
import ru.keplerbr.homepage.data.model.dto.TagDto;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ArticleDtoToArticleMapper {

  private final TagToDtoMapper tagToDtoMapper;

  public Article dtoToArticle(ArticleDto dto) {
    var article = new Article();

    article.setVisibility(dto.getVisibility());
    article.setTitle(dto.getTitle());
    article.setMarkdown(dto.getMarkdown());
    article.setLanguage(dto.getLanguage());
    article.setTags(tagToDtoMapper.toTagSet(dto.getTags()));

    return article;
  }

  public ArticleDto articleToDto(Article article) {
    if (Objects.isNull(article)) {
      return new ArticleDto();
    }
    var dto = new ArticleDto();

    dto.setVisibility(article.getVisibility());
    dto.setLanguage(article.getLanguage());
    dto.setTitle(article.getTitle());
    dto.setMarkdown(article.getMarkdown());
    dto.setRendered(article.getRendered());
    dto.setTags(tagToDtoMapper.toDtoSet(article.getTags()));
    dto.setCreatedAt(article.getCreatedAt());
    dto.setUpdatedAt(article.getUpdatedAt());

    return dto;
  }

  public Article patchArticleWithDto(Article article, ArticleDto dto) {
    if (Objects.nonNull(dto.getVisibility())) {
      article.setVisibility(dto.getVisibility());
    }
    if (Objects.nonNull(dto.getTitle())) {
      article.setTitle(dto.getTitle());
    }
    if (Objects.nonNull(dto.getMarkdown())) {
      article.setMarkdown(dto.getMarkdown());
    }
    if (Objects.nonNull(dto.getLanguage())) {
      article.setLanguage(dto.getLanguage());
    }
    if (Objects.nonNull(dto.getTags())) {
      Set<Tag> articleTags = article.getTags();
      Set<TagDto> dtoTags = dto.getTags();
      articleTags.addAll(tagToDtoMapper.toTagSet(dtoTags));
      article.setTags(articleTags);
    }

    return article;
  }

  public ArticleDto articleToDto(Article article, Set<String> includeFields) {
    var dto = new ArticleDto();

    for (var fieldName : includeFields) {
      switch (fieldName) {
        case "visibility":
          dto.setVisibility(article.getVisibility());
          break;
        case "language":
          dto.setLanguage(article.getLanguage());
          break;
        case "title":
          dto.setTitle(article.getTitle());
          break;
        case "markdown":
          dto.setMarkdown(article.getMarkdown());
          break;
        case "rendered":
          dto.setRendered(article.getRendered());
          break;
        case "tags":
          Set<Tag> articleTags = article.getTags();
          Set<TagDto> dtoTags = tagToDtoMapper.toDtoSet(articleTags);
          dto.setTags(dtoTags);
          break;
        case "createdAt":
          dto.setCreatedAt(article.getCreatedAt());
          break;
        case "updatedAt":
          dto.setUpdatedAt(article.getCreatedAt());
          break;
        default:
          break;
      }
    }

    return dto;
  }

}
