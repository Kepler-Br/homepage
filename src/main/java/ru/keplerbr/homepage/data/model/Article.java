package ru.keplerbr.homepage.data.model;

import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.keplerbr.homepage.data.model.enumerator.Language;
import ru.keplerbr.homepage.data.model.enumerator.Visibility;

@Data
@Entity
public class Article {

  public static final int MARKDOWN_MAX_LENGTH = 4096;
  public static final int TITLE_MAX_LENGTH = 1024;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private Long id;

  @Column(name = "VISIBILITY", nullable = false)
  @Enumerated(EnumType.ORDINAL)
  private Visibility visibility = Visibility.PRIVATE;

  @Column(name = "SLUG", nullable = false, unique = true)
  private String slug;

  @Column(name = "TITLE", nullable = false, length = TITLE_MAX_LENGTH)
  private String title;

  @Column(name = "MARKDOWN", nullable = false, length = MARKDOWN_MAX_LENGTH)
  private String markdown;

  @Column(name = "RENDERED", nullable = false, length = MARKDOWN_MAX_LENGTH * 4)
  private String rendered;

  @Column(name = "LANGUAGE", nullable = false)
  @Enumerated(EnumType.STRING)
  private Language language = Language.EN;

  @Column(name = "CREATED_AT", nullable = false)
  @CreationTimestamp
  private Date createdAt;

  @Column(name = "UPDATED_AT", nullable = false)
  @UpdateTimestamp
  private Date updatedAt;

  @OneToMany(mappedBy = "id")
  private Set<Tag> tags;

}
