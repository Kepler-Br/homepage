package ru.keplerbr.homepage.graphql.data.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.keplerbr.homepage.graphql.data.model.listener.ArticleTranslationListener;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@EntityListeners(ArticleTranslationListener.class)
@ToString(exclude = {"language", "article"})
public class ArticleTranslation {

    public static final int MARKDOWN_MAX_LENGTH = 4096;

    public static final int MARKDOWN_PREVIEW_MAX_LENGTH = 1024;

    public static final int TITLE_MAX_LENGTH = 1024;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TITLE", nullable = false, length = TITLE_MAX_LENGTH)
    @NotEmpty
    private String title;

    @Column(name = "MARKDOWN_PREVIEW", nullable = false, length = MARKDOWN_PREVIEW_MAX_LENGTH)
    @NotEmpty
    private String markdownPreview;

    @Column(name = "RENDERED_PREVIEW", nullable = false, length = MARKDOWN_PREVIEW_MAX_LENGTH * 4)
    private String renderedPreview;

    @Column(name = "MARKDOWN", nullable = false, length = MARKDOWN_MAX_LENGTH)
    @NotEmpty
    private String markdown;

    @Column(name = "RENDERED", nullable = false, length = MARKDOWN_MAX_LENGTH * 4)
    private String rendered;

    @ManyToOne(
            targetEntity = ArticleLanguage.class,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},
            fetch = FetchType.EAGER)
    private ArticleLanguage language;

    @ManyToOne(
            targetEntity = Article.class,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},
            fetch = FetchType.EAGER)
    private Article article;
}
