package ru.keplerbr.homepage.server.api.impl.v1.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.keplerbr.homepage.data.model.ErrorResponse;
import ru.keplerbr.homepage.data.model.Tag;
import ru.keplerbr.homepage.server.api.impl.v1.service.TagService;

@RestController
@RequestMapping(value = "${api.v1.path}/tag", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Validated
public class TagControllerV1 {

  private final TagService tagService;

  @GetMapping
  public ResponseEntity<List<Tag>> getAll() {
    return tagService.tags();
  }

  @GetMapping(path = "id/{id}")
  public ResponseEntity<Tag> getById(@PathVariable(name = "id") Long tagId) {
    return tagService.get(tagId);
  }

  @GetMapping(path = "name/{name}")
  public ResponseEntity<Tag> getByName(@PathVariable(name = "name") String tagName) {
    return tagService.get(tagName);
  }

  @DeleteMapping(path = "name/{name}")
  public ResponseEntity<Tag> deleteByName(@PathVariable(name = "name") String tagName) {
    return tagService.delete(tagName);
  }

  @DeleteMapping(path = "id/{id}")
  public ResponseEntity<Tag> deleteById(@PathVariable(name = "id") Long tagId) {
    return tagService.delete(tagId);
  }

  @PostMapping
  public ResponseEntity<Tag> create(
      @RequestParam
      @Pattern(regexp = "(?U)\\w+", message = "'name' should only contain letters and underscores")
          String name) {
    return tagService.create(name);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorResponse> constraintViolation(ConstraintViolationException exception) {
    List<String> errorMessages =
        exception.getConstraintViolations().stream()
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.toList());
    StringBuilder stringBuilder = new StringBuilder("Invalid fields: ");
    errorMessages.forEach(message -> stringBuilder.append(message).append("; "));
    return ResponseEntity
        .badRequest()
        .body(new ErrorResponse(stringBuilder.toString(), 100));
  }
}
