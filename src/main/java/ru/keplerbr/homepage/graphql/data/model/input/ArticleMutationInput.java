package ru.keplerbr.homepage.graphql.data.model.input;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.keplerbr.homepage.graphql.data.model.enumerator.Language;
import ru.keplerbr.homepage.graphql.data.model.enumerator.Visibility;

import java.util.Set;

@Data
@NoArgsConstructor
public class ArticleMutationInput {

  private Visibility visibility;

  private String title;

  private String markdown;

  private String markdownPreview;

  private Language language;

  private Set<TagMutationInput> tags;
}
