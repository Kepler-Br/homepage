package ru.keplerbr.homepage.graphql.data.model.listener;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.keplerbr.homepage.graphql.data.model.Article;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ArticleListener {

  private final Parser markdownParser;

  private final HtmlRenderer markdownRenderer;

  @PrePersist
  @PreUpdate
  public void updateRendered(Article article) {
    String markdown = article.getMarkdown();
    Node document = markdownParser.parse(markdown);
    String rendered = markdownRenderer.render(document);

    article.setRendered(rendered);
  }

}
