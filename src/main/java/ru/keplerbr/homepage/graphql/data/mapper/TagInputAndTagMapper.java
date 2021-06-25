package ru.keplerbr.homepage.graphql.data.mapper;

import org.springframework.stereotype.Component;
import ru.keplerbr.homepage.graphql.data.model.Tag;
import ru.keplerbr.homepage.graphql.data.model.dto.TagMutationInput;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TagInputAndTagMapper {

  public Tag toTag(TagMutationInput input) {
    return new Tag(input.getName());
  }

  public TagMutationInput toInput(Tag tag) {
    return new TagMutationInput(tag.getName());
  }

  public Set<Tag> toTagSet(Set<TagMutationInput> inputSet) {
    if (Objects.isNull(inputSet)) {
      return Collections.emptySet();
    }
    return inputSet.stream().map(tagDto -> new Tag(tagDto.getName())).collect(Collectors.toSet());
  }

  public Set<TagMutationInput> toInputSet(Set<Tag> tags) {
    if (Objects.isNull(tags)) {
      return Collections.emptySet();
    }
    return tags.stream()
        .map(tag -> new TagMutationInput(tag.getName()))
        .collect(Collectors.toSet());
  }
}
