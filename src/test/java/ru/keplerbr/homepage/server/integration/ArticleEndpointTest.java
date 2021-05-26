package ru.keplerbr.homepage.server.integration;

import org.junit.runner.RunWith;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.keplerbr.homepage.server.integration.config.ApiUrlsConfigurationProperties;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@EnableConfigurationProperties(ApiUrlsConfigurationProperties.class)
class ArticleEndpointTest {

//  private static MockMvc mockMvc;
//
//  @Autowired
//  private ApiUrlsConfigurationProperties apiUrls;
//
//  @Autowired
//  private ArticleRepository articleRepository;
//
//  @Autowired
//  public ArticleEndpointTest(WebApplicationContext wac) {
//    mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
//  }
//
//  @Test
//  void create_CreateValidArticle_ValidArticleReturned() throws Exception {
//    final String slug = "null";
//
////    ArticleAlternationRequest request = new ArticleAlternationRequest(
////        "Test",
////        "This is a test",
////        slug,
////        Collections.emptySet(),
////        Language.EN,
////        Visibility.PRIVATE);
////
////    mockMvc.perform(
////        post(apiUrls.getBase() + apiUrls.getArticleV1())
////            .contentType(MediaType.APPLICATION_JSON)
////            .content(new ObjectMapper().writeValueAsString(request)))
////        .andExpect(status().isCreated());
////
////    Optional<Article> articleOptional = articleRepository.findByUrl(slug);
////
////    Assertions.assertTrue(articleOptional.isPresent());
//  }
}
