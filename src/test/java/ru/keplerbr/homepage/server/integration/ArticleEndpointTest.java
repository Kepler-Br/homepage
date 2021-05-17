package ru.keplerbr.homepage.server.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.keplerbr.homepage.data.model.Article;
import ru.keplerbr.homepage.data.model.enumerator.Language;
import ru.keplerbr.homepage.data.model.enumerator.Visibility;
import ru.keplerbr.homepage.data.model.request.ArticleAlternationRequest;
import ru.keplerbr.homepage.data.repository.ArticleRepository;
import ru.keplerbr.homepage.server.integration.config.ApiUrlsConfigurationProperties;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@EnableConfigurationProperties(ApiUrlsConfigurationProperties.class)
class ArticleEndpointTest {

  private static MockMvc mockMvc;

  @Autowired
  private ApiUrlsConfigurationProperties apiUrls;

  @Autowired
  private ArticleRepository articleRepository;

  @Autowired
  public ArticleEndpointTest(WebApplicationContext wac) {
    mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
  }

  @Test
  void create_CreateValidArticle_ValidArticleReturned() throws Exception {
    final String slug = "null";

    ArticleAlternationRequest request = new ArticleAlternationRequest(
        "Test",
        "This is a test",
        slug,
        Collections.emptySet(),
        Language.EN,
        Visibility.PRIVATE);

    mockMvc.perform(
        post(apiUrls.getBase() + apiUrls.getArticleV1())
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(request)))
        .andExpect(status().isCreated());

    Optional<Article> articleOptional = articleRepository.findBySlug(slug);

    Assertions.assertTrue(articleOptional.isPresent());
  }
}
