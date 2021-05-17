package ru.keplerbr.homepage.server.api.impl.v1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.keplerbr.homepage.data.model.Article;
import ru.keplerbr.homepage.data.model.request.ArticleAlternationRequest;
import ru.keplerbr.homepage.server.api.impl.v1.service.ArticleService;

@RestController
@RequestMapping(value = "${api.v1.base}/article", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Validated
public class ArticleControllerV1 {

  private final ArticleService articleService;

  @GetMapping("slug/{slug}")
  public ResponseEntity<Article> getBySlug(@PathVariable(name = "slug") String slug) {
    return ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity<Article> create(@RequestBody ArticleAlternationRequest request) {
    return articleService.create(request);
  }

}
