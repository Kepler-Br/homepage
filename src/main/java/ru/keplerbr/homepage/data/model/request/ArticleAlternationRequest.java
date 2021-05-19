package ru.keplerbr.homepage.data.model.request;

import java.util.Set;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ru.keplerbr.homepage.data.model.Article;
import ru.keplerbr.homepage.data.model.enumerator.Language;
import ru.keplerbr.homepage.data.model.enumerator.Visibility;

@RequiredArgsConstructor
@Getter
public class ArticleAlternationRequest {

  @NotEmpty
  @Length(max = Article.MARKDOWN_MAX_LENGTH)
  private final String body;

  @NotEmpty
  @Length(max = Article.TITLE_MAX_LENGTH)
  private final String title;

  private final Set<String> tags;

  @NotNull
  private final Language language;

  @NotNull
  private final Visibility visibility;

}
