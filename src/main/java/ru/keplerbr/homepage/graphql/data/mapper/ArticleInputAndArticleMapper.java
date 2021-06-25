package ru.keplerbr.homepage.graphql.data.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.keplerbr.homepage.graphql.data.model.Article;
import ru.keplerbr.homepage.graphql.data.model.ArticleLanguage;
import ru.keplerbr.homepage.graphql.data.model.ArticleTranslation;
import ru.keplerbr.homepage.graphql.data.model.Tag;
import ru.keplerbr.homepage.graphql.data.model.dto.ArticleMutationInput;
import ru.keplerbr.homepage.graphql.data.model.dto.ArticleTranslationMutationInput;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ArticleInputAndArticleMapper {

  private final TagInputAndTagMapper tagInputAndTagMapper;

  public Article toArticle(ArticleMutationInput dto) {
    var article = new Article();

    article.setVisibility(dto.getVisibility());
    //    article.setTitle(dto.getTitle());
    //    article.setMarkdown(dto.getMarkdown());
    //    article.setMarkdownPreview(dto.getMarkdownPreview());
    //    article.setLanguage(new ArticleLanguage(dto.getLanguage()));
    //    article.setTags(tagInputAndTagMapper.toTagSet(new HashSet<>(dto.getTags())));
    article.setTags(dto.getTags().stream().map(Tag::new).collect(Collectors.toSet()));
    article.setTranslations(
        dto.getTranslations().stream()
            .map(
                translation -> {
                  var dbTranslation = new ArticleTranslation();

                  dbTranslation.setLanguage(new ArticleLanguage(translation.getLanguage()));
                  dbTranslation.setMarkdown(translation.getMarkdown());
                  dbTranslation.setMarkdownPreview(translation.getMarkdownPreview());
                  dbTranslation.setTitle(translation.getTitle());
                  dbTranslation.setArticle(article);
                  return dbTranslation;
                })
            .collect(Collectors.toList()));

    return article;
  }

  public ArticleMutationInput toInput(Article article) {
    if (Objects.isNull(article)) {
      return new ArticleMutationInput();
    }
    var dto = new ArticleMutationInput();

    dto.setVisibility(article.getVisibility());
    //    dto.setLanguage(article.getLanguage().getName());
    //    dto.setTitle(article.getTitle());
    //    dto.setMarkdown(article.getMarkdown());
    //    dto.setMarkdownPreview(article.getMarkdownPreview());
    //    dto.setRendered(article.getRendered());
    //    dto.setTags(new ArrayList<>(tagInputAndTagMapper.toDtoSet(article.getTags())));
    //    dto.setTags(tagInputAndTagMapper.toInputSet(article.getTags()));
    dto.setTags(article.getTags().stream().map(Tag::getName).collect(Collectors.toSet()));
    dto.setTranslations(
        article.getTranslations().stream()
            .map(
                translation -> {
                  var inputTranslation = new ArticleTranslationMutationInput();

                  inputTranslation.setLanguage(translation.getLanguage().getName());
                  inputTranslation.setMarkdown(translation.getMarkdown());
                  inputTranslation.setMarkdownPreview(translation.getMarkdownPreview());
                  inputTranslation.setTitle(translation.getTitle());
                  return inputTranslation;
                })
            .collect(Collectors.toSet()));

    //    dto.setCreatedAt(article.getCreatedAt());
    //    dto.setUpdatedAt(article.getUpdatedAt());

    return dto;
  }

  //  public Article patchArticleWithInput(Article article, ArticleMutationInput dto) {
  //    if (Objects.nonNull(dto.getVisibility())) {
  //      article.setVisibility(dto.getVisibility());
  //    }
  //    if (Objects.nonNull(dto.getTitle())) {
  //      article.setTitle(dto.getTitle());
  //    }
  //    if (Objects.nonNull(dto.getMarkdown())) {
  //      article.setMarkdown(dto.getMarkdown());
  //    }
  //    if (Objects.nonNull(dto.getMarkdownPreview())) {
  //      article.setMarkdownPreview(dto.getMarkdownPreview());
  //    }
  //    if (Objects.nonNull(dto.getLanguage())) {
  //      article.setLanguage(new ArticleLanguage(dto.getLanguage()));
  //    }
  //    return article;
  //  }

  //  public ArticleMutationInput toInput(Article article, Set<String> includeFields) {
  //    var dto = new ArticleMutationInput();
  //
  //    for (var fieldName : includeFields) {
  //      switch (fieldName) {
  //        case "visibility":
  //          dto.setVisibility(article.getVisibility());
  //          break;
  //        case "language":
  //          dto.setLanguage(article.getLanguage());
  //          break;
  //        case "title":
  //          dto.setTitle(article.getTitle());
  //          break;
  //        case "markdown":
  //          dto.setMarkdown(article.getMarkdown());
  //          break;
  //          //        case "rendered":
  //          //          dto.setRendered(article.getRendered());
  //          //          break;
  //        case "tags":
  //          Set<Tag> articleTags = article.getTags();
  //          Set<TagMutationInput> dtoTags = tagInputAndTagMapper.toInputSet(articleTags);
  //          dto.setTags(dtoTags);
  //          break;
  //          //        case "createdAt":
  //          //          dto.setCreatedAt(article.getCreatedAt());
  //          //          break;
  //          //        case "updatedAt":
  //          //          dto.setUpdatedAt(article.getCreatedAt());
  //          //          break;
  //        default:
  //          break;
  //      }
  //    }
  //
  //    return dto;
  //  }
}
