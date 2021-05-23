package ru.keplerbr.homepage.data.model.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TagDto {

  @JsonValue
  private String name;

}
