package connectingstar.tars.common.domain.converter;

import connectingstar.tars.common.enums.Codable;
import jakarta.persistence.AttributeConverter;
import org.springframework.util.StringUtils;

/**
 * JPA Entity Enum Converter
 *
 * @author 송병선
 */
public abstract class AbstractEnumCodeConverter<E extends Enum<E> & Codable> implements
        AttributeConverter<E, String> {

    public AbstractEnumCodeConverter() {
    }

    @Override
    public String convertToDatabaseColumn(E attribute) {
        return this.toDatabaseColumn(attribute);
    }

    @Override
    public E convertToEntityAttribute(String dbData) {
        return null;
    }

    public E toEntityAttribute(Class<E> cls, String code) {
        return !StringUtils.hasText(code) ? null : Codable.fromCode(cls, code);
    }

    private String toDatabaseColumn(E attr) {
        return (attr == null) ? null : attr.getCode();
    }
}
