package connectingstar.tars.user.controller;

import connectingstar.tars.user.command.UserOutCommandService;
import connectingstar.tars.user.domain.LoginUser;
import connectingstar.tars.user.domain.User;
import connectingstar.tars.user.jwt.JwtCommandService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import connectingstar.tars.common.response.SuccessResponse;
import connectingstar.tars.user.command.UserCommandService;
import connectingstar.tars.user.request.UserConstellationStarRequest;
import connectingstar.tars.user.request.UserOutReasonRequest;
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
  private final UserOutCommandService userOutCommandService;
  private final JwtCommandService jwtCommandService;

  @DeleteMapping("/{userId}")
  public ResponseEntity<?> deleteUser(@PathVariable Integer userId) {
    UserValidator.validate(userId);
    userCommandService.deleteUser(userId);

    return ResponseEntity.ok(new SuccessResponse());
  }

  /**
   * 사용자 별자리 별 등록
   *
   * @param param 별자리 정보
   * @return 요청 결과
   */
  @PostMapping(value = "/constellation/star")
  public ResponseEntity<?> postAsConstellationStar(@RequestBody UserConstellationStarRequest param) {
    UserValidator.validate(param);
    userCommandService.modifyStarCount(param);

    return ResponseEntity.ok(new SuccessResponse());
  }

  @GetMapping(value = "/userBasicInfo")
  public ResponseEntity<?> getUserBasicInfo(@LoginUser User loginUser) {
    //UserValidator.validate(loginUser); 필요없는 것 같은데 ...
    return ResponseEntity.ok(userCommandService.getUserBasicInfo(loginUser));
  }

  @GetMapping(value = "/userBasicInfoAndHabit")
  public ResponseEntity<?> getUserBasicInfoAndHabit(@LoginUser User loginUser) {
    return ResponseEntity.ok(userCommandService.getUserBasicInfoAndHabit(loginUser));
  }

  /**
   * 별자리 단일 조회 시 사용자 보유 여부 반환
   *
   * @param param 별자리 ID, 사용자 ID
   * @return 사용자 별자리 보유 여부
   */
  @GetMapping(value = "/one")
  public ResponseEntity<?> getHavingUserConstellation(UserConstellationSaveRequest param) {
    UserValidator.validate(param);
    return ResponseEntity.ok(userCommandService.getUserHavingConstellation(param));
  }

  @PostMapping(value = "/out")
  public ResponseEntity<?> postUserOutReason(@RequestBody UserOutReasonRequest param) {
    userOutCommandService.saveUserOut(param);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }
}
