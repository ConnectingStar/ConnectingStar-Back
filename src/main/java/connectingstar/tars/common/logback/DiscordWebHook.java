package connectingstar.tars.common.logback;

import connectingstar.tars.common.domain.JsonObject;
import java.awt.Color;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Discord에 WebHook 전송
 *
 * @author 송병선
 */
@Slf4j
@Setter
public class DiscordWebHook {

  private final String urlString;
  private final List<EmbedObject> embeds = new ArrayList<>();
  private String content;
  private String username;
  private String avatarUrl;
  private boolean tts;

  public DiscordWebHook(String urlString) {
    this.urlString = urlString;
  }

  public void addEmbed(EmbedObject embed) {
    this.embeds.add(embed);
  }

  /**
   * Discrod LogBack 메세지 전송
   */
  public void execute() {
    if (this.content == null && this.embeds.isEmpty()) {
      throw new IllegalArgumentException("컨텐츠를 설정하거나 하나 이상의 embedobject를 추가해야 합니다.");
    }

    try {
      WebClient.create(urlString)
          .post()
          .header("Content-Type", "application/json")
          .bodyValue(createDiscordEmbedObject(this.embeds, initializerDiscordSendForJsonObject(new JsonObject())).toString().getBytes(
              StandardCharsets.UTF_8))
          .retrieve()
          .toBodilessEntity()
          .subscribe((e) -> {
          }, (error) -> {
            log.info("에러 메세지: " + error.getMessage());
          });

    } catch (Exception e) {
      log.error("에러 메세지: " + e.getMessage());
    }
  }

  private JsonObject initializerDiscordSendForJsonObject(JsonObject json) {
    json.put("content", this.content);
    json.put("username", this.username);
    json.put("avatar_url", this.avatarUrl);
    json.put("tts", this.tts);
    return json;
  }

  private JsonObject createDiscordEmbedObject(List<EmbedObject> embeds, JsonObject json) {
    if (embeds.isEmpty()) {
      throw new IllegalArgumentException("EmbedObject는 1개 이상 존재해야합니다.");
    }

    List<JsonObject> embedObjects = new ArrayList<>();

    for (EmbedObject embed : embeds) {
      JsonObject jsonEmbed = new JsonObject();

      jsonEmbed.put("title", embed.getTitle());
      jsonEmbed.put("description", embed.getDescription());
      jsonEmbed.put("url", embed.getUrl());

      setEmbedColor(embed, jsonEmbed);
      setEmbedMessageFields(embed.getFields(), jsonEmbed);

      embedObjects.add(jsonEmbed);
    }
    json.put("embeds", embedObjects.toArray());

    return json;
  }

  /**
   * Embed 메세지 테두리 색상 설정
   */
  private void setEmbedColor(EmbedObject embed, JsonObject jsonEmbed) {
    if (embed.getColor() != null) {
      Color color = embed.getColor();
      int rgb = color.getRed();
      rgb = (rgb << 8) + color.getGreen();
      rgb = (rgb << 8) + color.getBlue();

      jsonEmbed.put("color", rgb);
    }
  }

  /**
   * 텍스트 필드 목록 설정
   */
  private void setEmbedMessageFields(List<Field> fields, JsonObject jsonEmbed) {
    List<JsonObject> jsonFields = new ArrayList<>();

    for (Field field : fields) {
      JsonObject jsonField = new JsonObject();

      jsonField.put("name", field.getName());
      jsonField.put("value", field.getValue());
      jsonField.put("inline", field.isInline());

      jsonFields.add(jsonField);
    }

    jsonEmbed.put("fields", jsonFields.toArray());
  }
}