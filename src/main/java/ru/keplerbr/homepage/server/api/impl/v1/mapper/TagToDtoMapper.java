package ru.keplerbr.homepage.server.api.impl.v1.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import ru.keplerbr.homepage.data.model.Tag;
import ru.keplerbr.homepage.data.model.dto.TagDto;

@Component
public class TagToDtoMapper {

  Tag toTag(TagDto tagDto) {
    return new Tag(tagDto.getName());
  }

  TagDto toDto(Tag tag) {
    return new TagDto(tag.getName());
  }

  Set<Tag> toTagSet(Set<TagDto> dots) {
    return dots.stream().map(tagDto -> new Tag(tagDto.getName())).collect(Collectors.toSet());
  }

  Set<TagDto> toDtoSet(Set<Tag> tags) {
    return tags.stream().map(tag -> new TagDto(tag.getName())).collect(Collectors.toSet());
  }

}
