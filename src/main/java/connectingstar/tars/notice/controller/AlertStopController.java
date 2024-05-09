package connectingstar.tars.notice.controller;

import connectingstar.tars.common.response.SuccessResponse;
import connectingstar.tars.notice.command.AlertStopCommandService;
import connectingstar.tars.notice.request.AlertStopDeleteRequest;
import connectingstar.tars.notice.request.AlertStopRequest;
import connectingstar.tars.notice.validation.AlertStopValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/alert", produces = MediaType.APPLICATION_JSON_VALUE)
public class AlertStopController {

  private final AlertStopCommandService alertStopCommandService;

  @PostMapping
  public ResponseEntity<?> createAlertStop(@RequestBody AlertStopRequest alertStopRequest) {
    alertStopCommandService.saveAlertStop(alertStopRequest);
    return ResponseEntity.ok(new SuccessResponse());
  }

  @DeleteMapping
  public ResponseEntity<?> deleteAlertStop(@RequestBody AlertStopDeleteRequest param) {
    AlertStopValidator.validate(param);
    alertStopCommandService.removeAlertStop(param);
    return ResponseEntity.ok(new SuccessResponse());
  }

}
