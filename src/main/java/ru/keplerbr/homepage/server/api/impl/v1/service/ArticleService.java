package ru.keplerbr.homepage.server.api.impl.v1.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.net.URI;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.keplerbr.homepage.data.model.Article;
import ru.keplerbr.homepage.data.model.Tag;
import ru.keplerbr.homepage.data.model.dto.ArticleDto;
import ru.keplerbr.homepage.data.model.dto.TagDto;
import ru.keplerbr.homepage.data.model.exception.NotFoundException;
import ru.keplerbr.homepage.data.repository.ArticleRepository;
import ru.keplerbr.homepage.data.repository.TagRepository;
import ru.keplerbr.homepage.data.utils.IdBasedUriGenerator;
import ru.keplerbr.homepage.server.api.impl.v1.mapper.ArticleDtoToArticleMapper;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
//@Validated
public class ArticleService {

  private static final String SERVICE_NAME = "Article";

  private static final long MIN_URL_LENGTH = 4;

  private final ArticleDtoToArticleMapper mapper;

  private final ArticleRepository repository;

  private final TagRepository tagRepository;

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Value("${api.v1.base}")
  private String baseUri;

  public ResponseEntity<Object> create(ArticleDto articleDto) {
    @Valid Article article = mapper.dtoToArticle(articleDto);

    article = repository.save(article);
    Long articleId = article.getId();
    String articleUrl = IdBasedUriGenerator.generate(SERVICE_NAME, articleId, MIN_URL_LENGTH);

    article.setUrl(articleUrl);
    repository.save(article);

    return ResponseEntity.created(
        URI.create(
            String.format("%s/acrticle/%s", baseUri, article.getUrl())
        )).build();
  }

  public ResponseEntity<ArticleDto> get(String url, Set<String> fields) {
    Optional<Article> articleOptional = repository.findByUrl(url);

    if (articleOptional.isEmpty()) {
      throw new NotFoundException(String.format("Article with url '%s' was not found.", url));
    }

    Article article = articleOptional.get();

    ArticleDto fullArticleDto = mapper.articleToDto(article);

    ObjectNode jsonNode = objectMapper.valueToTree(fullArticleDto);

    jsonNode.retain(fields);

    ArticleDto articleDto;

    try {
      articleDto = objectMapper.treeToValue(jsonNode, ArticleDto.class);
    } catch (JsonProcessingException e) {
      log.error("Serialize error", e);
      articleDto = new ArticleDto();
    }

    return ResponseEntity.ok(articleDto);
  }

  public ResponseEntity<Object> patch(String url, ArticleDto articlePatch) {
    Optional<Article> articleOptional = repository.getByUrl(url);

    if (articleOptional.isEmpty()) {
      throw new NotFoundException(String.format("Article with url '%s' cannot be found.", url));
    }
    Article article = articleOptional.get();

    article = mapper.patchArticleWithDto(article, articlePatch);

//    Set<Tag> articleTags = article.getTags();
    Set<String> newArticleNames = articlePatch.getTags().stream().map(TagDto::getName).collect(Collectors.toSet());

    Set<Tag> existingTags = tagRepository.findAllByNameIn(newArticleNames);

    repository.save(article);
    return ResponseEntity.ok().build();
  }

}
