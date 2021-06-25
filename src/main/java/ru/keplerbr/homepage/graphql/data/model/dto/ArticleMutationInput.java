package ru.keplerbr.homepage.graphql.data.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.keplerbr.homepage.graphql.data.model.enumerator.Language;
import ru.keplerbr.homepage.graphql.data.model.enumerator.Visibility;

import java.util.Set;

@Data
@NoArgsConstructor
public class ArticleMutationInput {

  private Visibility visibility;

  private Set<ArticleTranslationMutationInput> translations;

  private Set<String> tags;
}
