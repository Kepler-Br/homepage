package ru.keplerbr.homepage.graphql.data.model.listener;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.keplerbr.homepage.graphql.data.model.Article;
import ru.keplerbr.homepage.graphql.data.model.ArticleTranslation;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ArticleTranslationListener {

  private final Parser markdownParser;

  private final HtmlRenderer markdownRenderer;

  private String renderMarkdown(String markdown) {
    Node document = markdownParser.parse(markdown);
    return markdownRenderer.render(document);
  }

  @PrePersist
  @PreUpdate
  public void updateRendered(ArticleTranslation article) {
    String markdown = article.getMarkdown();

    article.setRendered(renderMarkdown(markdown));

    markdown = article.getMarkdownPreview();
    article.setRenderedPreview(renderMarkdown(markdown));
  }
}
