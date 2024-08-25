package connectingstar.tars.common.converter;

import connectingstar.tars.common.enums.SortOrder;
import org.springframework.core.convert.converter.Converter;

public class StringToSortOrderConverter implements Converter<String, SortOrder> {
    @Override
    public SortOrder convert(String source) {
        return SortOrder.fromString(source);
    }
}
