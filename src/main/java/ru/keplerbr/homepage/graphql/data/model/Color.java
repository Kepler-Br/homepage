package ru.keplerbr.homepage.graphql.data.model;

import javax.persistence.Column;
import lombok.Data;

@Data
public class Color {

  @Column(name = "RED")
  private float red;

  @Column(name = "GREEN")
  private float green;

  @Column(name = "BLUE")
  private float blue;

}
