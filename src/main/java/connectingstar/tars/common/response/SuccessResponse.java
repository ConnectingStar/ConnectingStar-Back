package connectingstar.tars.common.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.ToString;

/**
 * 성공 응답
 *
 * @author 송병선
 */
@Getter
public class SuccessResponse {
  private final int status = HttpStatus.OK.value();
  private final String message = "success";
}
