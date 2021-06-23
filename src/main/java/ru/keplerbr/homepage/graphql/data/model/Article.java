package ru.keplerbr.homepage.graphql.data.model;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.UpdateTimestamp;
import ru.keplerbr.homepage.graphql.data.model.enumerator.Language;
import ru.keplerbr.homepage.graphql.data.model.enumerator.Visibility;
import ru.keplerbr.homepage.graphql.data.model.listener.ArticleListener;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@ToString(exclude = "tags")
@EntityListeners(ArticleListener.class)
public class Article {

  public static final int MARKDOWN_MAX_LENGTH = 4096;

  public static final int MARKDOWN_PREVIEW_MAX_LENGTH = 1024;

  public static final int TITLE_MAX_LENGTH = 1024;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private Long id;

  @Column(name = "VISIBILITY", nullable = false)
  @Enumerated(EnumType.STRING)
  private Visibility visibility = Visibility.PRIVATE;

  @Column(name = "URL", unique = true)
  @NaturalId(mutable = true)
  private String url;

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

  @Column(name = "LANGUAGE", nullable = false)
  @Enumerated(EnumType.STRING)
  private Language language = Language.EN;

  @Column(name = "CREATED_AT", updatable = false)
  @CreationTimestamp
  private Date createdAt;

  @Column(name = "UPDATED_AT")
  @UpdateTimestamp
  private Date updatedAt;

  @ManyToMany(
      targetEntity = Tag.class,
      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},
      fetch = FetchType.EAGER)
  private Set<Tag> tags;
}
