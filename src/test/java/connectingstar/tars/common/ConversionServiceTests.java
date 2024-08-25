package connectingstar.tars.common;

import connectingstar.tars.common.converter.StringToEnumConverterFactory;
import org.junit.jupiter.api.Test;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConversionServiceTests {
    public enum MockEnum {
        MOCK1,
        MOCK_VALUE,
    }

    @Test
    void convertsCamelCaseStringToEnum() {
        // given
        FormattingConversionService conversionService = new DefaultFormattingConversionService();
        conversionService.addConverterFactory(new StringToEnumConverterFactory());

        // when
        MockEnum mockEnum = conversionService.convert("mockValue", MockEnum.class);

        // then
        assertEquals(mockEnum, MockEnum.MOCK_VALUE);
    }
}
