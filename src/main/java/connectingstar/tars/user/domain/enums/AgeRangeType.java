package connectingstar.tars.user.domain.enums;

import connectingstar.tars.common.domain.converter.AbstractEnumCodeConverter;
import connectingstar.tars.common.domain.enums.Codable;
import connectingstar.tars.common.response.TypeResponse;
import jakarta.persistence.Converter;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

/**
 * 회원 나이대 구분 코드
 *
 * @author 송병선
 */
@Getter
@RequiredArgsConstructor
public enum AgeRangeType implements Codable {

  AGE_15("A", "15-19"),
  AGE_20("B", "20-24"),
  AGE_25("C", "25-29"),
  AGE_30("D", "30-34"),
  AGE_35("E", "35-39"),
  AGE_40("F", "40-44"),
  AGE_45("G", "45-49"),
  AGE_50("H", "50-54"),
  AGE_55("I", "55 이상"),
  NONE("N", "선택 안 함");

  private final String code;
  private final String name;

  public static List<TypeResponse> getTypeList() {
    return Stream.of(AgeRangeType.values()).map(it -> new TypeResponse(it.getCode(), it.getName())).toList();
  }

  public static AgeRangeType fromCode(String code) {
    return Codable.fromCode(AgeRangeType.class, code);
  }

  public static boolean containCode(String code) {
    if (!StringUtils.hasText(code)) {
      return false;
    }
    return EnumSet.allOf(AgeRangeType.class).stream().anyMatch(it -> it.getCode().equals(code));
  }

  @Converter
  public static class TypeCodeConverter extends AbstractEnumCodeConverter<AgeRangeType> {

    @Override
    public AgeRangeType convertToEntityAttribute(String dbData) {
      return this.toEntityAttribute(AgeRangeType.class, dbData);
    }
  }
}
