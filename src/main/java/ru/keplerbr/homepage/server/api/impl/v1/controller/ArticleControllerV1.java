package ru.keplerbr.homepage.server.api.impl.v1.controller;

import java.util.List;
import javax.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.keplerbr.homepage.data.model.Article;
import ru.keplerbr.homepage.data.model.response.ConstraintViolationResponse;
import ru.keplerbr.homepage.data.model.response.ErrorResponse;
import ru.keplerbr.homepage.data.model.enumerator.ErrorType;
import ru.keplerbr.homepage.data.model.enumerator.Language;
import ru.keplerbr.homepage.data.model.enumerator.Visibility;
import ru.keplerbr.homepage.data.model.request.ArticleAlternationRequest;
import ru.keplerbr.homepage.server.api.impl.v1.service.ArticleService;

@RestController
@RequestMapping(value = "${api.v1.base}/article", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Validated
public class ArticleControllerV1 {

  private final ArticleService articleService;

//  @GetMapping("slug/{slug}")
//  public ResponseEntity<Article> getBySlug(@PathVariable(name = "slug") String slug) {
//    return ResponseEntity.notFound().build();
//  }

  @PostMapping
  public ResponseEntity<Article> create(@RequestBody ArticleAlternationRequest request) {
    return articleService.create(request);
  }

  @GetMapping("{url}")
  public ResponseEntity<Article> getByUrl(
      @PathVariable(name = "url") String url,
      @RequestParam(name = "fields", required = false) List<String> fields) {

  }

//  @PostMapping("create")
//  public ResponseEntity<Article> create() {
//    var request = new ArticleAlternationRequest("body", "title", null, Language.EN,
//        Visibility.PRIVATE);
//    return articleService.create(request);
//  }

  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  public ResponseEntity<ErrorResponse> notApplicationJsonMediaTypeException(
      HttpMediaTypeNotSupportedException ex) {
    var response = new ErrorResponse(
        "Not supported content type. Did you set it to application/json?",
        ErrorType.UNSUPPORTED_MEDIA_TYPE.getCode());

    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(response);
  }

  @ExceptionHandler(value = {ConstraintViolationException.class})
  public ResponseEntity<Object> constraintViolationException(
      ConstraintViolationException ex) {
    var response = new ConstraintViolationResponse(
        "Request body fields have incorrect format.",
        ErrorType.BAD_REQUEST.getCode());
    ex.getConstraintViolations().forEach((violation) -> {
      String fieldName = null;

      for (var node : violation.getPropertyPath()) {
        fieldName = node.getName();
      }
      response.addViolatedField(fieldName, violation.getMessage());
    });

    return ResponseEntity.badRequest().body(response);
  }

}
