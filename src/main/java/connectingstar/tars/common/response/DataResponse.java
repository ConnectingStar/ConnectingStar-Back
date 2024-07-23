package connectingstar.tars.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

/**
 * 단일 데이터 공통 응답 형식
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataResponse<TData> extends SuccessResponse {

    private final TData data;

    public DataResponse(TData data) {
        this.data = data;
    }
}