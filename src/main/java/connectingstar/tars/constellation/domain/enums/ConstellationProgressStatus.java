package connectingstar.tars.constellation.domain.enums;

import connectingstar.tars.common.domain.converter.AbstractEnumCodeConverter;
import connectingstar.tars.common.domain.enums.Codable;
import jakarta.persistence.Converter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 별자리 진행 상태 코드
 *
 * @author 송병선
 */
@Getter
@RequiredArgsConstructor
public enum ConstellationProgressStatus implements Codable {

  /**
   * 진행중인 별자리
   */
  NONE("N"),
  /**
   * 진행중인 별자리
   */
  OTHER("O"),
  /**
   * 진행중인 별자리
   */
  PROGRESS("P"),
  /**
   * 진행중인 별자리
   */
  COMPLETE("C");

  private final String code;

  @Converter
  public static class TypeCodeConverter extends AbstractEnumCodeConverter<ConstellationProgressStatus> {

    @Override
    public ConstellationProgressStatus convertToEntityAttribute(String dbData) {
      return this.toEntityAttribute(ConstellationProgressStatus.class, dbData);
    }
  }
}
