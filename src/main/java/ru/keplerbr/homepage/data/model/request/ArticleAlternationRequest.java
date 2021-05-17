package ru.keplerbr.homepage.data.model.request;

import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.keplerbr.homepage.data.model.enumerator.Language;
import ru.keplerbr.homepage.data.model.enumerator.Visibility;

@RequiredArgsConstructor
@Getter
public class ArticleAlternationRequest {

  private final String body;

  private final String title;

  private final String slug;

  private final Set<String> tags;

  private final Language language;

  private final Visibility visibility;

}
