package connectingstar.tars.oauth.domain.enums;

import connectingstar.tars.common.domain.converter.AbstractEnumCodeConverter;
import connectingstar.tars.common.enums.Codable;
import jakarta.persistence.Converter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.EnumSet;

/**
 * 소셜 타입
 *
 * @author 송병선
 */
@Getter
@RequiredArgsConstructor
public enum SocialType implements Codable {

    /**
     * 카카오
     */
    KAKAO("K");

    private final String code;

    public static boolean containCode(String code) {
        if (!StringUtils.hasText(code)) {
            return false;
        }
        return EnumSet.allOf(SocialType.class).stream().anyMatch(e -> e.getCode().equals(code));
    }

    public static SocialType fromCode(String code) {
        return Codable.fromCode(SocialType.class, code);
    }

    @Converter
    public static class TypeCodeConverter extends AbstractEnumCodeConverter<SocialType> {

        @Override
        public SocialType convertToEntityAttribute(String dbData) {
            return this.toEntityAttribute(SocialType.class, dbData);
        }
    }
}
