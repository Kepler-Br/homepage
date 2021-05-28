package ru.keplerbr.homepage.graphql.data.model;

import lombok.Data;

import javax.persistence.Column;

@Data
public class Color {

  @Column(name = "RED")
  private float red;

  @Column(name = "GREEN")
  private float green;

  @Column(name = "BLUE")
  private float blue;
}
