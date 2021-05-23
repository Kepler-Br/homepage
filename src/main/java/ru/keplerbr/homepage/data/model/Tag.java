package ru.keplerbr.homepage.data.model;

import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Pattern;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.annotations.NaturalId;

@Data
@Entity
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

  public Tag() {
  }

  public Tag(@NonNull String name) {
    this.name = name;
  }
}
