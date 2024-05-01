package connectingstar.tars.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Collection;
import java.util.Objects;
import lombok.Getter;

/**
 * 목록 조회 공통 응답 형식
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListResponse extends SuccessResponse {

  private final Collection<?> list;
  private final int count;

  public ListResponse(Collection<?> list) {
    this.list = list;
    this.count = Objects.isNull(list) ? 0 : list.size();
  }
}
