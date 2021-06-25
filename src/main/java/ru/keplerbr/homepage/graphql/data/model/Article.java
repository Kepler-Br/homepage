package ru.keplerbr.homepage.graphql.data.model;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.UpdateTimestamp;
import ru.keplerbr.homepage.graphql.data.model.enumerator.Visibility;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Entity
@ToString(of = {"tags", "translations"})
public class Article {

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

  @Column(name = "CREATED_AT", updatable = false)
  @CreationTimestamp
  private Date createdAt;

  @Column(name = "UPDATED_AT")
  @UpdateTimestamp
  private Date updatedAt;

  @OneToMany(
      targetEntity = ArticleTranslation.class,
      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},
      fetch = FetchType.EAGER,
      mappedBy = "article")
  private List<ArticleTranslation> translations;

  @ManyToMany(
      targetEntity = Tag.class,
      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},
      fetch = FetchType.EAGER)
  private Set<Tag> tags;
}
