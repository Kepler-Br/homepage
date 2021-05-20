package ru.keplerbr.homepage.server.api.impl.v1.controller;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.keplerbr.homepage.data.model.Article;
import ru.keplerbr.homepage.data.model.enumerator.ErrorType;
import ru.keplerbr.homepage.data.model.request.ArticleAlternationRequest;
import ru.keplerbr.homepage.data.model.dto.ArticleDto;
import ru.keplerbr.homepage.data.model.response.ErrorResponse;
import ru.keplerbr.homepage.server.api.impl.v1.service.ArticleService;

@RestController
@RequestMapping(value = "${api.v1.base}/article", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ArticleControllerV1 {

  private final ArticleService articleService;

  @PostMapping
  public ResponseEntity<Object> create(@RequestBody ArticleDto articleDto) {
    return articleService.create(articleDto);
  }

  @GetMapping("{url}")
  public ResponseEntity<ArticleDto> getByUrl(
      @PathVariable(name = "url") String url,
      @RequestParam(name = "fields", required = false, defaultValue = "") Set<String> fields) {
    return articleService.get(url, fields);
  }

  @PatchMapping("{url}")
  public ResponseEntity<Object> patch(
      @PathVariable(name = "url") String url,
      @RequestBody ArticleDto articleDto) {
    return articleService.patch(url, articleDto);
  }

  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  public ResponseEntity<ErrorResponse> notApplicationJsonMediaTypeException(
      HttpMediaTypeNotSupportedException ex) {
    var response = new ErrorResponse(
        "Not supported content type. Did you set it to application/json?",
        ErrorType.UNSUPPORTED_MEDIA_TYPE.getCode());

    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(response);
  }

}
