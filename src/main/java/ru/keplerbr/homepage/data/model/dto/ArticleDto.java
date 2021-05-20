package ru.keplerbr.homepage.data.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
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
@JsonInclude(Include.NON_NULL)
public class ArticleDto {

  private Visibility visibility;

  private String title;

  private String markdown;

  private String rendered;

  private Language language;

  private Date createdAt;

  private Date updatedAt;

  private Set<Tag> tags;

}
