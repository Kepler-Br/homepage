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
  public static final int MARKDOWN_MAX_LENGTH = 4096;
  public static final int TITLE_MAX_LENGTH = 1024;

  public enum Visibility {
    ADMIN,
    LINK,
    ALL
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private Long id;

  @Column(name = "VISIBILITY", nullable = false)
  @Enumerated(EnumType.ORDINAL)
  private Visibility visibility = Visibility.ADMIN;

  @Column(name = "TITLE", nullable = false, length = TITLE_MAX_LENGTH)
  private String title;

  @Column(name = "MARKDOWN", nullable = false, length = MARKDOWN_MAX_LENGTH)
  private String markdown;

  @Column(name = "RENDERED", nullable = false, length = MARKDOWN_MAX_LENGTH * 4)
  private String rendered;

  @OneToMany(mappedBy = "ID")
  private Set<Tag> tags;
}
