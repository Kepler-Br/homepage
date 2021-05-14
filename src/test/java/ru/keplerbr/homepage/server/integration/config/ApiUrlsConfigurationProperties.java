package ru.keplerbr.homepage.server.integration.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("api.urls")
@RequiredArgsConstructor
@Getter
@Setter
public class ApiUrlsConfigurationProperties {

  private String base;

  private String tagV1;

}
