package connectingstar.tars.common.converter;

import com.querydsl.core.types.Order;
import connectingstar.tars.common.enums.SortOrder;
import org.springframework.core.convert.converter.Converter;

public class SortOrderToQueryDslOrderConverter implements Converter<SortOrder, Order> {
    @Override
    public Order convert(SortOrder source) {
        switch (source) {
            case ASC:
                return Order.ASC;
            case DESC:
                return Order.DESC;
            default:
                throw new IllegalArgumentException("No querydsl enum with " + this + " found");
        }
    }
}
