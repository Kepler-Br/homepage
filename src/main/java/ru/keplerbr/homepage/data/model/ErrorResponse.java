package ru.keplerbr.homepage.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {

  private String message;

  private int errorCode;

}
