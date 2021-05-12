package ru.keplerbr.homepage.data.model;

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

@Data
@Entity
public class Note {

  public enum Visibility {
    ADMIN,
    LINK,
    ALL
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private Long id;

  @Column(name = "VISIBILITY")
  @Enumerated(EnumType.STRING)
  private Visibility visibility = Visibility.ADMIN;

  @Column(name = "TEXT")
  private String text;

  @OneToMany(mappedBy = "id")
  private Set<Tag> tags;
}
