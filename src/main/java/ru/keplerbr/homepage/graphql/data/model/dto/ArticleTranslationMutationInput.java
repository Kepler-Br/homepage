package ru.keplerbr.homepage.graphql.data.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "language")
public class ArticleTranslationMutationInput {

    private String title;

    private String markdown;

    private String markdownPreview;

    private String language;
}
