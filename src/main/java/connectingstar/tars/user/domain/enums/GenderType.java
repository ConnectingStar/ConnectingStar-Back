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
 * 회원 성별 구분 코드
 *
 * @author 송병선
 */
@Getter
@RequiredArgsConstructor
public enum GenderType implements Codable {

  MALE("M", "남"),
  FEMALE("F", "여"),
  NONE("N", "선택 안 함");

  private final String code;
  private final String name;

  public static List<TypeResponse> getTypeList() {
    return Stream.of(GenderType.values()).map(it -> new TypeResponse(it.getCode(), it.getName())).toList();
  }

  public static GenderType fromCode(String code) {
    return Codable.fromCode(GenderType.class, code);
  }

  public static boolean containCode(String code) {
    if (!StringUtils.hasText(code)) {
      return false;
    }
    return EnumSet.allOf(GenderType.class).stream().anyMatch(it -> it.getCode().equals(code));
  }

  @Converter
  public static class TypeCodeConverter extends AbstractEnumCodeConverter<GenderType> {

    @Override
    public GenderType convertToEntityAttribute(String dbData) {
      return this.toEntityAttribute(GenderType.class, dbData);
    }
  }
}
