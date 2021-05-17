package ru.keplerbr.homepage.server.api.impl.v1.service;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.keplerbr.homepage.data.model.Tag;
import ru.keplerbr.homepage.data.model.exception.AlreadyExistsException;
import ru.keplerbr.homepage.data.model.exception.TagNotFoundByIdException;
import ru.keplerbr.homepage.data.model.exception.TagNotFoundByNameException;
import ru.keplerbr.homepage.data.repository.TagRepository;


@Transactional
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Validated
public class TagService {

  private final TagRepository tagRepository;

  @Value("${api.v1.base}")
  private String baseUri;

  public ResponseEntity<Tag> delete(String name) {
    long total = tagRepository.deleteByName(name);
    if (total == 0L) {
      throw new TagNotFoundByNameException(name);
    }
    return ResponseEntity.noContent().build();
  }

  public ResponseEntity<Tag> delete(Long id) {
    try {
      tagRepository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new TagNotFoundByIdException(id);
    }

    return ResponseEntity.noContent().build();
  }

  public ResponseEntity<Tag> create(String name) {
    if (tagRepository.existsByName(name)) {
      throw new AlreadyExistsException(String.format("Tag with name '%s' already exists.", name));
    }
    Tag tag = tagRepository.save(new Tag(name));

    return ResponseEntity
        .created(URI.create(String.format("%s/name/%s", baseUri, tag.getName())))
        .body(tag);
  }

  public ResponseEntity<Tag> get(String name) {
    Tag tag = tagRepository
        .findByName(name)
        .orElseThrow(() -> new TagNotFoundByNameException(name));

    return ResponseEntity.ok(tag);
  }

  public ResponseEntity<Tag> get(Long id) {
    Tag tag = tagRepository
        .findById(id)
        .orElseThrow(() -> new TagNotFoundByIdException(id));

    return ResponseEntity.ok(tag);
  }

  public ResponseEntity<List<Tag>> tags() {
    return ResponseEntity.ok(tagRepository.findAll());
  }
}
