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
class TagEndpointTest {

  //  @Autowired
  //  private MockMvc mockMvc;
  //
  //  @Autowired
  //  private TagRepository tagRepository;
  //
  //  @Autowired
  //  private ApiUrlsConfigurationProperties apiUrls;
  //
  //  @Transactional
  //  protected Tag createTag(String name) {
  //    return tagRepository.save(new Tag(name));
  //  }
  //
  //  @Transactional
  //  protected boolean tagExistsByName(String name) {
  //    Optional<Tag> optionalTag = tagRepository.findByName(name);
  //    return optionalTag.isPresent();
  //  }
  //
  //  @Transactional
  //  @BeforeEach
  //  public void clearTagTable() {
  //    tagRepository.deleteAll();
  //  }
  //
  //  @Test
  //  void create_CreateTag_ReturnedCorrectNewTag() throws Exception {
  //    final String tagName = "NewTag";
  //
  //    mockMvc.perform(post(apiUrls.getBase() + apiUrls.getTagV1() + "?name={tagName}", tagName))
  //        .andExpect(status().isCreated())
  //        .andExpect(content()
  //            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
  //        .andExpect(jsonPath("$.name").value(tagName));
  //
  //    Assertions.assertTrue(tagExistsByName(tagName));
  //  }
  //
  //  @Test
  //  void create_CreateSameTagTwice_ErrorResponseReturned() throws Exception {
  //    final String tagName = "NewTagSame";
  //
  //    createTag(tagName);
  //
  //    mockMvc.perform(post(apiUrls.getBase() + apiUrls.getTagV1() + "?name={tagName}", tagName))
  //        .andExpect(status().isConflict())
  //        .andExpect(content()
  //            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
  //        .andExpect(jsonPath("$.errorCode").isNumber())
  //        .andExpect(jsonPath("$.message").isString());
  //  }
  //
  //  @Test
  //  void create_CreateUnicodeTag_ReturnedCorrectNewTag() throws Exception {
  //    final String tagName = "NewЮникодеTag";
  //
  //    mockMvc.perform(post(apiUrls.getBase() + apiUrls.getTagV1() + "?name={tagName}", tagName))
  //        .andExpect(status().isCreated())
  //        .andExpect(content()
  //            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
  //        .andExpect(jsonPath("$.name").value(tagName));
  //
  //    Assertions.assertTrue(tagExistsByName(tagName));
  //  }
  //
  //  @Test
  //  void create_CreateInvalidTag_ErrorResponseReturned() throws Exception {
  //    final String tagName = "New%20Invalid%20Tag";
  //
  //    mockMvc.perform(post(apiUrls.getBase() + apiUrls.getTagV1() + "?name={tagName}", tagName))
  //        .andExpect(status().isBadRequest())
  //        .andExpect(content()
  //            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
  //        .andExpect(jsonPath("$.errorCode").isNumber())
  //        .andExpect(jsonPath("$.message").isString());
  //    ;
  //    Assertions.assertFalse(tagExistsByName(tagName));
  //  }
  //
  //  @Test
  //  void deleteByName_DeleteExistingTag_HttpNoContentReturned() throws Exception {
  //    final String tagName = "TagDeleteByName";
  //
  //    createTag(tagName);
  //
  //    mockMvc.perform(delete(apiUrls.getBase() + apiUrls.getTagV1() + "/name/{tagName}", tagName))
  //        .andExpect(status().isNoContent());
  //
  //    Assertions.assertFalse(tagExistsByName(tagName));
  //  }
  //
  //  @Test
  //  void deleteById_DeleteExistingTag_HttpNoContentReturned() throws Exception {
  //    final String tagName = "TagDeleteById";
  //
  //    Tag tag = createTag(tagName);
  //
  //    mockMvc.perform(delete(apiUrls.getBase() + apiUrls.getTagV1() + "/id/{tagId}", tag.getId()))
  //        .andExpect(status().isNoContent());
  //
  //    Assertions.assertFalse(tagExistsByName(tagName));
  //  }
  //
  //  @Test
  //  void deleteByName_DeleteNotExistingTag_ErrorResponseReturned() throws Exception {
  //    final String tagName = "NotExists";
  //
  //    mockMvc.perform(delete(apiUrls.getBase() + apiUrls.getTagV1() + "/name/{tagName}", tagName)
  //        .contentType(MediaType.APPLICATION_JSON))
  //        .andExpect(status().isNotFound())
  //        .andExpect(content()
  //            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
  //        .andExpect(jsonPath("$.errorCode").isNumber())
  //        .andExpect(jsonPath("$.message").isString());
  //  }
  //
  //  @Test
  //  void deleteById_DeleteNotExistingTag_ErrorResponseReturned() throws Exception {
  //    final Long tagId = 101L;
  //
  //    mockMvc.perform(delete(apiUrls.getBase() + apiUrls.getTagV1() + "/id/{tagName}", tagId)
  //        .contentType(MediaType.APPLICATION_JSON))
  //        .andExpect(status().isNotFound())
  //        .andExpect(content()
  //            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
  //        .andExpect(jsonPath("$.errorCode").isNumber())
  //        .andExpect(jsonPath("$.message").isString());
  //    ;
  //  }

}
