package ru.keplerbr.homepage.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class UriGeneratorTask {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private Long id;

  @Column(name = "SERVICE_NAME", nullable = false, unique = true)
  private String serviceName;

  @Column(name = "URI_LENGTH", nullable = false)
  private int uriLength = 5;

  public UriGeneratorTask() {
  }

  public UriGeneratorTask(String serviceName) {
    this.serviceName = serviceName;
  }

}
