package connectingstar.tars.history.request.param;

import connectingstar.tars.common.validator.PatternList;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * GET /v2/histories/:id 의 request param.
 *
 * @author 이우진
 */
@Getter
@Setter
public class HistoryGetOneRequestParam {
    @Nullable
    @PatternList(regexp = "^(runHabit)$", message = "'related' param 값이 유효하지 않습니다.")
    private List<String> related;
}

