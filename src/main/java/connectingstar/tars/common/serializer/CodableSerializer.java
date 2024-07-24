package connectingstar.tars.common.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import connectingstar.tars.common.enums.Codable;

import java.io.IOException;

/**
 * Codable enum을 code로 직렬화하는 Serializer.
 * JSON 응답 출력용으로 사용.
 *
 * @author 이우진
 */
public class CodableSerializer extends StdSerializer<Codable> {

    public CodableSerializer() {
        super(Codable.class);
    }

    @Override
    public void serialize(Codable codable, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (codable == null) {
            jsonGenerator.writeNull();
        } else {
            jsonGenerator.writeString(codable.getCode());
        }
    }
}