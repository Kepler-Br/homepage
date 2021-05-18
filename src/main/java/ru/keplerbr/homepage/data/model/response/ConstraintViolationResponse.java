package ru.keplerbr.homepage.data.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
public class ConstraintViolationResponse {

  private String message;

  private int errorCode;

  @JsonProperty("fields")
  private Map<String, String> violatedFieldsWithMessages;

  public ConstraintViolationResponse(String message, int errorCode) {
    this.message = message;
    this.errorCode = errorCode;
    violatedFieldsWithMessages = new HashMap<>();
  }

  public void addViolatedField(String fieldName, String message) {
    violatedFieldsWithMessages.put(fieldName, message);
  }

}
