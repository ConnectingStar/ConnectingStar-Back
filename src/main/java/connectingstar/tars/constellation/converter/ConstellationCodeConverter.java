package connectingstar.tars.constellation.converter;

import connectingstar.tars.constellation.enums.ConstellationCode;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ConstellationCodeConverter implements AttributeConverter<ConstellationCode, String> {

    @Override
    public String convertToDatabaseColumn(ConstellationCode attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public ConstellationCode convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        for (ConstellationCode code : ConstellationCode.values()) {
            if (code.getValue().equals(dbData)) {
                return code;
            }
        }
        throw new IllegalArgumentException("Unknown database value: " + dbData);
    }
}