package ru.keplerbr.homepage.graphql.data.model.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagMutationInput {

  private String name;

}
