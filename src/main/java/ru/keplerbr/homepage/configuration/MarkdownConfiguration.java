package ru.keplerbr.homepage.configuration;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MarkdownConfiguration {

  private final MutableDataSet options;

  public MarkdownConfiguration() {
    options = new MutableDataSet();

    // uncomment to set optional extensions
    // options.set(Parser.EXTENSIONS, Arrays.asList(TablesExtension.create(),
    // StrikethroughExtension.create()));

    // uncomment to convert soft-breaks to hard breaks
    // options.set(HtmlRenderer.SOFT_BREAK, "<br />\n");
  }

  @Bean
  public Parser markdownParserBean() {
    return Parser.builder(options).build();
  }

  @Bean
  public HtmlRenderer markdownHtmlRendererBean() {
    return HtmlRenderer.builder(options).build();
  }
}
