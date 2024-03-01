package connectingstar.tars.user.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import connectingstar.tars.common.response.SuccessResponse;
import connectingstar.tars.user.command.UserCommandService;
import connectingstar.tars.user.request.UserConstellationSaveRequest;
import connectingstar.tars.user.validation.UserValidator;
import lombok.RequiredArgsConstructor;


/**
 * 회원 관련 API
 *
 * @author 송병선
 */
@RequiredArgsConstructor
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class UserController {

  private final UserCommandService userCommandService;

  @PostMapping(value = "/constellation")
  public ResponseEntity<?> postAsConstellation(@RequestBody UserConstellationSaveRequest param) {
    UserValidator.validate(param);
    userCommandService.saveConstellation(param);

    return ResponseEntity.ok(new SuccessResponse());
  }
}
