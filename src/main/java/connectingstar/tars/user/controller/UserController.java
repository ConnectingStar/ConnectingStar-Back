package connectingstar.tars.user.controller;

import connectingstar.tars.common.response.SuccessResponse;
import connectingstar.tars.user.command.UserCommandService;
import connectingstar.tars.user.command.UserOutCommandService;
import connectingstar.tars.user.request.UserConstellationStarRequest;
import connectingstar.tars.user.request.UserOutReasonRequest;
import connectingstar.tars.user.validation.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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

  @DeleteMapping
  public ResponseEntity<?> deleteUser() {
    userCommandService.deleteUser();
    return ResponseEntity.ok(new SuccessResponse());
  }

  /**
   * 별자리 단일 조회 시 사용자 보유 여부 반환
   *
   * @param param 별자리 ID, 사용자 ID
   * @return 사용자 별자리 보유 여부
   */
  @GetMapping(value = "/one")
  public ResponseEntity<?> getHavingUserConstellation(UserConstellationStarRequest param) {
    UserValidator.validate(param);
    return ResponseEntity.ok(userCommandService.getUserHavingConstellation(param));
  }

  /**
   * 유저 기본 정보 반환
   *
   * @return 유저 닉네임 + 정체성
   */
  @GetMapping(value = "/userBasicInfo")
  public ResponseEntity<?> getUserBasicInfo() {
    return ResponseEntity.ok(userCommandService.getUserBasicInfo());
  }

  /**
   * 유저 기본 정보 반환
   *
   * @return 유저 닉네임 + 정체성 + 습관
   */
  @GetMapping(value = "/userBasicInfoAndHabit")
  public ResponseEntity<?> getUserBasicInfoAndHabit() {
    return ResponseEntity.ok(userCommandService.getUserBasicInfoAndHabit());
  }

  /**
   * 사용자 별자리 별 등록
   *
   * @param param 별자리 정보
   * @return 요청 결과
   */
  @PostMapping(value = "/constellation/star")
  public ResponseEntity<?> postAsConstellationStar(
      @RequestBody UserConstellationStarRequest param) {
    UserValidator.validate(param);

    userCommandService.modifyStarCount(param);
    return ResponseEntity.ok(new SuccessResponse());
  }

  @PostMapping(value = "/out")
  public ResponseEntity<?> postUserOutReason(@RequestBody UserOutReasonRequest param) {
    userOutCommandService.saveUserOut(param);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }
}
