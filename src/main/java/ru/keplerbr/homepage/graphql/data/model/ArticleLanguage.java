package ru.keplerbr.homepage.graphql.data.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(of = "name")
@ToString(of = {"id", "name"})
public class ArticleLanguage {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private Long id;

  @Column(name = "NAME", unique = true, nullable = false)
  @NaturalId
  private String name;

  @OneToMany(targetEntity = ArticleTranslation.class, mappedBy = "language", fetch = FetchType.EAGER)
  private List<ArticleTranslation> articleTranslations;

  public ArticleLanguage() {}

  public ArticleLanguage(@NonNull String name) {
    this.name = name;
  }
}
