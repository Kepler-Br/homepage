package ru.keplerbr.homepage.server.api.impl.v1.service;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.keplerbr.homepage.data.model.Article;
import ru.keplerbr.homepage.data.model.exception.NotFoundException;
import ru.keplerbr.homepage.data.model.request.ArticleAlternationRequest;
import ru.keplerbr.homepage.data.model.response.ArticleResponse;
import ru.keplerbr.homepage.data.repository.ArticleRepository;
import ru.keplerbr.homepage.data.utils.IdBasedUriGenerator;
import ru.keplerbr.homepage.server.api.impl.v1.mapper.ArticleRequestToArticleMapper;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
//@Validated
public class ArticleService {

  private static final String SERVICE_NAME = "Article";

  private final ArticleRequestToArticleMapper mapper;

  private final ArticleRepository repository;

  @Value("${api.v1.base}")
  private String baseUri;

  public ResponseEntity<Article> create(@Valid ArticleAlternationRequest request) {
    Article article = mapper.requestToArticle(request);

    article = repository.save(article);
    Long articleId = article.getId();
    String articleUrl = IdBasedUriGenerator.generate(SERVICE_NAME, articleId, 4);
    article.setUrl(articleUrl);
    repository.save(article);

    return ResponseEntity.created(
        URI.create(
            String.format("%s/acrticle/%s", baseUri, article.getUrl())
        )).build();
  }

  public ResponseEntity<ArticleResponse> get(String url, List<String> fields) {
    Optional<Article> articleOptional = repository.findByUrl(url);

    if (articleOptional.isEmpty()) {
      throw new NotFoundException(String.format("Article with url '%s' was not found,", url));
    }

    Article article = articleOptional.get();

    article.setId(null);
    if (!fields.contains("mardown")) {
      article.setMarkdown(null);
    }

    ResponseEntity r = ResponseEntity.ok(article);
    return r;
  }

}
