package ru.keplerbr.homepage.data.model.exception;

import lombok.Getter;

@Getter
public class TagNotFoundByIdException extends NotFoundException {

  private static final String messagePattern = "Tag with id \"%d\" was not found in the database.";
  private static final int errorCode = 100;

  private final Long tagId;

  public TagNotFoundByIdException(Long tagId) {
    super(String.format(messagePattern, tagId), errorCode);

    this.tagId = tagId;
  }
}
