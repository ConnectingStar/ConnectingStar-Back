package connectingstar.tars.common.logback;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Discord 메세지 데이터 구조
 *
 * @author 송병선
 */
public class EmbedObject {

  /**
   * 추가 정보를 표시하기 위해 사용되는 텍스트 필드 목록
   */
  private final List<Field> fields = new ArrayList<>();
  /**
   * Embed의 주제목
   */
  private String title;
  /**
   * Discord Embed Message 상세 내용<
   */
  private String description;
  /**
   * Embed를 클릭할 때 이동할 URL
   */
  private String url;
  /**
   * Embed 메세지 테두리 색상
   */
  private Color color;

  public String getTitle() {
    return title;
  }

  public EmbedObject setTitle(String title) {
    this.title = title;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public EmbedObject setDescription(String description) {
    this.description = description;
    return this;
  }

  public String getUrl() {
    return url;
  }

  public EmbedObject setUrl(String url) {
    this.url = url;
    return this;
  }

  public Color getColor() {
    return color;
  }

  public EmbedObject setColor(Color color) {
    this.color = color;
    return this;
  }

  public List<Field> getFields() {
    return fields;
  }

  public EmbedObject addField(String name, String value, boolean inline) {
    this.fields.add(new Field(name, value, inline));
    return this;
  }
}
