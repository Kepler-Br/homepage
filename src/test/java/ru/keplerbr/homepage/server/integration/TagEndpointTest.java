package ru.keplerbr.homepage.server.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import ru.keplerbr.homepage.data.model.Tag;
import ru.keplerbr.homepage.data.repository.TagRepository;
import ru.keplerbr.homepage.server.integration.config.ApiUrlsConfigurationProperties;

@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
@ActiveProfiles("test")
@EnableConfigurationProperties(ApiUrlsConfigurationProperties.class)
public class TagEndpointTest {

  private static MockMvc mockMvc;

  @Autowired
  private TagRepository tagRepository;

  @Autowired
  private ApiUrlsConfigurationProperties apiUrls;

  @Autowired
  public TagEndpointTest(WebApplicationContext wac) {
    mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
  }

  @Transactional
  protected Tag createTag(String name) {
    return tagRepository.save(new Tag(name));
  }

  @Transactional
  protected boolean tagExistsByName(String name) {
    Optional<Tag> optionalTag = tagRepository.findByName(name);
    return optionalTag.isPresent();
  }

  @Transactional
  @BeforeEach
  public void clearTagTable() {
    tagRepository.deleteAll();
  }

  @Test
  public void create_CreateTag_ReturnedCorrectNewTag() throws Exception {
    final String tagName = "NewTag";

    mockMvc.perform(post(apiUrls.getBase() + apiUrls.getTagV1() + "?name={tagName}", tagName))
        .andExpect(status().isCreated())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.name").value(tagName));

    Assertions.assertTrue(tagExistsByName(tagName));
  }

  @Test
  public void create_CreateUnicodeTag_ReturnedCorrectNewTag() throws Exception {
    final String tagName = "NewЮникодеTag";

    mockMvc.perform(post(apiUrls.getBase() + apiUrls.getTagV1() + "?name={tagName}", tagName))
        .andExpect(status().isCreated())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.name").value(tagName));

    Assertions.assertTrue(tagExistsByName(tagName));
  }

  @Test
  public void create_CreateInvalidTag_ErrorResponseReturned() throws Exception {
    final String tagName = "New%20Invalid%20Tag";

    mockMvc.perform(post(apiUrls.getBase() + apiUrls.getTagV1() + "?name={tagName}", tagName))
        .andExpect(status().isBadRequest())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorCode").isNumber());
    Assertions.assertFalse(tagExistsByName(tagName));
  }

  @Test
  public void deleteByName_DeleteExistingTag_HttpNoContentReturned() throws Exception {
    final String tagName = "TagDeleteByName";

    createTag(tagName);

    mockMvc.perform(delete(apiUrls.getBase() + apiUrls.getTagV1() + "/name/{tagName}", tagName))
        .andExpect(status().isNoContent());

    Assertions.assertFalse(tagExistsByName(tagName));
  }

  @Test
  public void deleteById_DeleteExistingTag_HttpNoContentReturned() throws Exception {
    final String tagName = "TagDeleteById";

    Tag tag = createTag(tagName);

    mockMvc.perform(delete(apiUrls.getBase() + apiUrls.getTagV1() + "/id/{tagId}", tag.getId()))
        .andExpect(status().isNoContent());

    Assertions.assertFalse(tagExistsByName(tagName));
  }

  @Test
  public void deleteByName_DeleteNotExistingTag_ErrorResponseReturned() throws Exception {
    final String tagName = "NotExists";

    mockMvc.perform(delete(apiUrls.getBase() + apiUrls.getTagV1() + "/name/{tagName}", tagName)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

  @Test
  public void deleteById_DeleteNotExistingTag_ErrorResponseReturned() throws Exception {
    final Long tagId = 101L;

    mockMvc.perform(delete(apiUrls.getBase() + apiUrls.getTagV1() + "/id/{tagName}", tagId)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

}
