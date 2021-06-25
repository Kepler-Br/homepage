package ru.keplerbr.homepage.graphql.resolver;

import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.keplerbr.homepage.graphql.data.mapper.ArticleInputAndArticleMapper;
import ru.keplerbr.homepage.graphql.data.mapper.TagInputAndTagMapper;
import ru.keplerbr.homepage.graphql.data.model.Article;
import ru.keplerbr.homepage.graphql.data.model.ArticleLanguage;
import ru.keplerbr.homepage.graphql.data.model.ArticleTranslation;
import ru.keplerbr.homepage.graphql.data.model.Tag;
import ru.keplerbr.homepage.graphql.data.model.exception.GraphQLNotFoundException;
import ru.keplerbr.homepage.graphql.data.model.dto.ArticleMutationInput;
import ru.keplerbr.homepage.graphql.data.repository.ArticleLanguageRepository;
import ru.keplerbr.homepage.graphql.data.repository.ArticleRepository;
import ru.keplerbr.homepage.graphql.data.repository.TagRepository;
import ru.keplerbr.homepage.graphql.data.utils.IdBasedUriGenerator;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class ArticleMutationResolver implements GraphQLMutationResolver {

  public static final String SERVICE_NAME = "Article";

  public static final Long MIN_URL_LENGTH = 4L;

  private final ArticleInputAndArticleMapper articleMapper;

  private final TagInputAndTagMapper tagMapper;

  private final ArticleRepository articleRepository;

  private final TagRepository tagRepository;

  private final ArticleLanguageRepository articleLanguageRepository;

  private void updateTagsFromRepository(Set<Tag> tagSet) {
    Set<String> tagsAsStrings = tagSet.stream().map(Tag::getName).collect(Collectors.toSet());
    Set<Tag> foundTags = tagRepository.findAllByNameIn(tagsAsStrings);

    tagSet.removeAll(foundTags);
    tagSet.addAll(foundTags);
  }

  private void updateLanguageInTranslationsFromRepository(List<ArticleTranslation> translations) {
    Set<String> languageNames = translations.stream().map(translation -> translation.getLanguage().getName()).collect(Collectors.toSet());

    Map<String, ArticleLanguage> validLanguages = articleLanguageRepository.findAllByNameIn(languageNames).stream().collect(Collectors.toMap(ArticleLanguage::getName, Function.identity()));
    translations.forEach(translation -> {
      var languageName = translation.getLanguage().getName();

      if (validLanguages.containsKey(languageName)) {
        translation.setLanguage(validLanguages.get(languageName));
      }
    });
  }

  @Transactional
  public String deleteArticle(String url) {
    long deleted = articleRepository.deleteByUrl(url);

    if (deleted == 0) {
      throw new GraphQLNotFoundException(
          String.format("Article with url \"%s\" was not found", url));
    }
    return url;
  }

  @Transactional
  public Article editArticle(String url, ArticleMutationInput inputArticle) {
    throw new GraphQLNotFoundException("Not implemented");
//    Article article =
//        articleRepository
//            .getByUrl(url)
//            .orElseThrow(
//                () ->
//                    new GraphQLNotFoundException(
//                        String.format("Article with url \"%s\" was not found", url)));
//
////    articleMapper.patchArticleWithInput(article, inputArticle);
////    Set<Tag> updatedTags = tagMapper.toTagSet(inputArticle.getTags());
//    Set<Tag> updatedTags = inputArticle.getTags()
//            .stream()
//            .map(Tag::new)
//            .collect(Collectors.toSet());
//
//    updateTagsFromRepository(updatedTags);
//    article.setTags(updatedTags);
//    article = articleRepository.save(article);
//    return article;
  }

  @Transactional
  public Article createArticle(ArticleMutationInput inputArticle) {
    Article article = articleMapper.toArticle(inputArticle);

    updateTagsFromRepository(article.getTags());
    updateLanguageInTranslationsFromRepository(article.getTranslations());

    article = articleRepository.save(article);

    Long articleId = article.getId();
    String articleUrl = IdBasedUriGenerator.generate(SERVICE_NAME, articleId, MIN_URL_LENGTH);

    article.setUrl(articleUrl);
    article = articleRepository.save(article);

    return article;
  }
}
