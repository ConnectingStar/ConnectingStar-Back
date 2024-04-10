package connectingstar.tars.user.domain.enums;

import connectingstar.tars.common.domain.converter.AbstractEnumCodeConverter;
import connectingstar.tars.common.domain.enums.Codable;
import jakarta.persistence.Converter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GenderType implements Codable {

  MALE("M"),
  FEMALE("F"),
  NONE("N");

  private final String code;

  @Converter
  public static class TypeCodeConverter extends AbstractEnumCodeConverter<GenderType> {

    @Override
    public GenderType convertToEntityAttribute(String dbData) {
      return this.toEntityAttribute(GenderType.class, dbData);
    }
  }
}
