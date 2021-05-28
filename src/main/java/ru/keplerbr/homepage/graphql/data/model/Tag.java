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
@ToString(exclude = "articles")
public class Tag {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private Long id;

  @Column(name = "NAME", unique = true, nullable = false)
  @Pattern(regexp = "(?U)\\w+")
  @NaturalId
  private String name;

  @ManyToMany(targetEntity = Article.class, mappedBy = "tags", fetch = FetchType.EAGER)
  private List<Article> articles;

  public Tag() {}

  public Tag(@NonNull String name) {
    this.name = name;
  }
}
