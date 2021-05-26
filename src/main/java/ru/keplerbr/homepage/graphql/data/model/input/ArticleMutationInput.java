package ru.keplerbr.homepage.graphql.data.model.input;

import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.keplerbr.homepage.graphql.data.model.enumerator.Language;
import ru.keplerbr.homepage.graphql.data.model.enumerator.Visibility;

@Data
@NoArgsConstructor
public class ArticleMutationInput {

  private Visibility visibility;

  private String title;

  private String markdown;

  private Language language;

  private Set<TagMutationInput> tags;

}
