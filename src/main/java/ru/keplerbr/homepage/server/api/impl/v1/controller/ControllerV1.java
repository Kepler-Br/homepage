package ru.keplerbr.homepage.server.api.impl.v1.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.keplerbr.homepage.data.model.Tag;
import ru.keplerbr.homepage.data.repository.TagRepository;

@RestController
@RequestMapping(value = "${api.v1.path}", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ControllerV1 {

  private final TagRepository tagRepository;

  @GetMapping(path = "tag")
  public List<Tag> tag() {
    return tagRepository.findAll();
  }

  @PostMapping(path = "tag")
  public Tag createTag(@RequestParam String name) {
    Tag tag = new Tag();

    tag.setName(name);
    return tagRepository.save(tag);
  }

  @DeleteMapping(path = "tag")
  public Tag deleteTag(@RequestParam String name) {
    Tag tag = tagRepository.findByName(name);

    tagRepository.delete(tag);
    return tag;
  }

}
