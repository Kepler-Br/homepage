package ru.keplerbr.homepage.graphql.data.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import ru.keplerbr.homepage.graphql.data.model.Tag;
import ru.keplerbr.homepage.graphql.data.model.input.TagMutationInput;

@Component
public class TagInputAndTagMapper {

  public Tag toTag(TagMutationInput tagDto) {
    return new Tag(tagDto.getName());
  }

  public TagMutationInput toInput(Tag tag) {
    return new TagMutationInput(tag.getName());
  }

  public Set<Tag> toTagSet(Set<TagMutationInput> dots) {
    return dots.stream().map(tagDto -> new Tag(tagDto.getName())).collect(Collectors.toSet());
  }

  public Set<TagMutationInput> toInputSet(Set<Tag> tags) {
    return tags.stream().map(tag -> new TagMutationInput(tag.getName()))
        .collect(Collectors.toSet());
  }

}
