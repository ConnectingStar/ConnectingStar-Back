package connectingstar.tars.user.controller;

import connectingstar.tars.user.domain.LoginUser;
import connectingstar.tars.user.domain.User;
import connectingstar.tars.user.jwt.JwtCommandService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import connectingstar.tars.common.response.SuccessResponse;
import connectingstar.tars.user.command.UserCommandService;
import connectingstar.tars.user.request.UserConstellationSaveRequest;
import connectingstar.tars.user.validation.UserValidator;
import lombok.RequiredArgsConstructor;


/**
 * 회원 관련 API
 *
 * @author 송병선
 * @author 김규리
 */
@RequiredArgsConstructor
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class UserController {

  private final UserCommandService userCommandService;
  private final JwtCommandService jwtCommandService;

  @PostMapping(value = "/constellation")
  public ResponseEntity<?> postAsConstellation(@RequestBody UserConstellationSaveRequest param) {
    UserValidator.validate(param);
    userCommandService.saveConstellation(param);

    return ResponseEntity.ok(new SuccessResponse());
  }

  @GetMapping(value = "/userBasicInfo")
  public ResponseEntity<?> getUserBasicInfo(@LoginUser User loginUser) {
    //UserValidator.validate(loginUser); 필요없는 것 같은데 ...
    return ResponseEntity.ok(userCommandService.getUserBasicInfo(loginUser));
  }
}
