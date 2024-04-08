package connectingstar.tars.common.response;

import connectingstar.tars.common.exception.errorcode.ErrorCode;

public record ErrorResponse(int status, String message) {
  
  public ErrorResponse(ErrorCode errorCode) {
    this(errorCode.getHttpStatus().value(), errorCode.getMessage());
  }
}
