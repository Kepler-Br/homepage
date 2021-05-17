package ru.keplerbr.homepage.server.api.impl.v1.service;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.keplerbr.homepage.data.model.Article;
import ru.keplerbr.homepage.data.model.request.ArticleAlternationRequest;
import ru.keplerbr.homepage.data.repository.ArticleRepository;
import ru.keplerbr.homepage.server.api.impl.v1.mapper.ArticleRequestToArticleMapper;

@Transactional
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Validated
public class ArticleService {

  private static final String SERVICE_NAME = "Article";

  private final ArticleRequestToArticleMapper mapper;

  private final ArticleRepository repository;

  @Value("${api.v1.base}")
  private String baseUri;

  public ResponseEntity<Article> create(ArticleAlternationRequest request) {
    Article article = mapper.fromRequest(request);

    article = repository.save(article);
    return ResponseEntity.created(
        URI.create(
            String.format("%s/acrticle/%s", baseUri, "article.getSlug()")
        )).build();
  }

}
