package connectingstar.tars.common.config;

import connectingstar.tars.common.converter.SortOrderToQueryDslOrderConverter;
import connectingstar.tars.common.converter.StringToSortOrderConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {

        registry.addConverter(new StringToSortOrderConverter());
        registry.addConverter(new SortOrderToQueryDslOrderConverter());
    }
}
