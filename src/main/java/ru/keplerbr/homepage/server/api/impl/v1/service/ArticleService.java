package ru.keplerbr.homepage.server.api.impl.v1.service;

import java.net.URI;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.keplerbr.homepage.data.model.Article;
import ru.keplerbr.homepage.data.model.dto.ArticleDto;
import ru.keplerbr.homepage.data.model.exception.NotFoundException;
import ru.keplerbr.homepage.data.model.request.ArticleAlternationRequest;
import ru.keplerbr.homepage.data.repository.ArticleRepository;
import ru.keplerbr.homepage.data.utils.IdBasedUriGenerator;
import ru.keplerbr.homepage.server.api.impl.v1.mapper.ArticleDtoToArticleMapper;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
//@Validated
public class ArticleService {

  private static final String SERVICE_NAME = "Article";

  private static final long MIN_URL_LENGTH = 4;

  private final ArticleDtoToArticleMapper mapper;

  private final ArticleRepository repository;

  private final Set<String> defaultIncludeFields = new HashSet<>() {{
    add("title");
    add("rendered");
    add("url");
  }};

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

    fields.addAll(defaultIncludeFields);
    ArticleDto articleDto = mapper.articleToDto(article, fields);

    return ResponseEntity.ok(articleDto);
  }

  public ResponseEntity<Object> patch(String url, ArticleDto articlePatch) {
    return ResponseEntity.ok().build();
  }

}
