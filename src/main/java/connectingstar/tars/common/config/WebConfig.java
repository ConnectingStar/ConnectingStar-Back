package connectingstar.tars.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import connectingstar.tars.common.converter.SortOrderToQueryDslOrderConverter;
import connectingstar.tars.common.converter.StringToEnumConverterFactory;
import connectingstar.tars.common.converter.StringToSortOrderConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToSortOrderConverter());
        registry.addConverter(new SortOrderToQueryDslOrderConverter());
        registry.addConverterFactory(new StringToEnumConverterFactory());
    }

    /**
     * HTTP Message Converter 설정
     * <p>
     * response JSON time format - ISO 8601 String 포맷으로 설정
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // Remove the default MappingJackson2HttpMessageConverter
        converters.removeIf(converter -> {
            String converterName = converter.getClass().getSimpleName();
            return converterName.equals("MappingJackson2HttpMessageConverter");
        });

        // Add custom MappingJackson2HttpMessageConverter
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        converter.setObjectMapper(objectMapper);
        converters.add(converter);
        WebMvcConfigurer.super.extendMessageConverters(converters);
    }
}
