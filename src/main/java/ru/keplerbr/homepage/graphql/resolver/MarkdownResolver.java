package ru.keplerbr.homepage.graphql.resolver;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class MarkdownResolver implements GraphQLQueryResolver {

  private final Parser markdownParser;

  private final HtmlRenderer markdownRenderer;

  public String markdown(String input) {
    Node document = markdownParser.parse(input);

    return markdownRenderer.render(document);
  }

}
