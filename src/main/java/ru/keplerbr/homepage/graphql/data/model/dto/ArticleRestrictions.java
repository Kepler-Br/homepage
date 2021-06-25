package ru.keplerbr.homepage.graphql.data.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class ArticleRestrictions {

  Integer maxTitleLength;

  Integer maxBodyLength;

  Integer maxPreviewLength;

  List<String> languages;
}
