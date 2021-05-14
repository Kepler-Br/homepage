package ru.keplerbr.homepage.data.model.exception;

import lombok.Getter;

@Getter
public class TagNotFoundByNameException extends NotFoundException {

  private static final String messagePattern = "Tag with name \"%s\" was not found in the database.";

  private final String tagName;

  public TagNotFoundByNameException(String tagName) {
    super(String.format(messagePattern, tagName));

    this.tagName = tagName;
  }
}
