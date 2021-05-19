package ru.keplerbr.homepage.data.model.response;

import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;
import ru.keplerbr.homepage.data.model.Tag;
import ru.keplerbr.homepage.data.model.enumerator.Language;
import ru.keplerbr.homepage.data.model.enumerator.Visibility;

@Data
public class ArticleResponse {

  private Visibility visibility;

  private String url;

  private String title;

  private String markdown;

  private String rendered;

  private Language language;

  private Date createdAt;

  private Date updatedAt;

  private Set<String> tags;
}
