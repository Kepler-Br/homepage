package ru.keplerbr.homepage.data.model;

import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.annotations.NaturalId;
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

  @Column(name = "URL", unique = true)
  @NaturalId
  private String url;

  @Column(name = "TITLE", nullable = false, length = TITLE_MAX_LENGTH)
  @NotEmpty
  private String title;

  @Column(name = "MARKDOWN", nullable = false, length = MARKDOWN_MAX_LENGTH)
  @NotEmpty
  private String markdown;

  @Column(name = "RENDERED", nullable = false, length = MARKDOWN_MAX_LENGTH * 4)
  private String rendered;

  @Column(name = "LANGUAGE", nullable = false)
  @Enumerated(EnumType.STRING)
  private Language language = Language.EN;

  @Column(name = "CREATED_AT", nullable = false, updatable = false)
  private Date createdAt;

  @Column(name = "UPDATED_AT", nullable = false)
  @UpdateTimestamp
  private Date updatedAt;

  @ManyToMany(targetEntity = Tag.class, cascade = {CascadeType.PERSIST,
      CascadeType.MERGE}, fetch = FetchType.EAGER)
  private Set<Tag> tags;

  @PrePersist
  public void beforeCreated() {
    rendered = markdown;
    createdAt = new Date();
    updatedAt = (Date) createdAt.clone();
  }

  @PreUpdate
  public void beforeUpdated() {
    rendered = markdown;
    updatedAt = new Date();
  }

}
