package connectingstar.tars.user.controller;

import connectingstar.tars.common.response.SuccessResponse;
import connectingstar.tars.user.command.DeleteAccountReasonCommandService;
import connectingstar.tars.user.command.UserCommandService;
import connectingstar.tars.user.command.UserConstellationCommandService;
import connectingstar.tars.user.request.UserConstellationCreateRequest;
import connectingstar.tars.user.request.UserConstellationStarRequest;
import connectingstar.tars.user.request.UserOutReasonRequest;
import connectingstar.tars.user.validation.UserValidator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
  private final UserConstellationCommandService userConstellationCommandService;
  private final DeleteAccountReasonCommandService deleteAccountReasonCommandService;

  @DeleteMapping
  public ResponseEntity<?> deleteUser(HttpServletRequest request) {
    String accessToken = request.getHeader("Authorization");
    userCommandService.deleteUser(accessToken);
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

  /*
  별 관련 Controller
   */

  /**
   * 유저가 총 몇 개의 별을 가지고 있는지
   *
   * @return 유저 보유 별 갯수
   */
  @GetMapping(value = "/star")
  public ResponseEntity<?> getUserStar() {
    return ResponseEntity.ok(userCommandService.getUserStar());
  }

  /**
   * 진행 중인 별자리와 사용중인 별 갯수 조회
   *
   * @param constellationId 별자리 아이디
   * @return
   */
  @GetMapping(value = "/constellation")
  public ResponseEntity<?> getUserConstellation(
      @RequestParam(required = false) Integer constellationId) {
    return ResponseEntity.ok(
        userConstellationCommandService.getWorkingUserConstellation(constellationId));
  }

  /**
   * 진행중인 별자리 별 등록
   *
   * @param param 별자리 정보
   * @return 요청 결과
   */
  @PutMapping(value = "/constellation/star")
  public ResponseEntity<?> putAsConstellationStar(@RequestBody UserConstellationStarRequest param) {
    UserValidator.validate(param);

    userConstellationCommandService.modifyStarCount(param);
    return ResponseEntity.ok(new SuccessResponse());
  }

  /**
   * 별자리 선택
   *
   * @param param 별자리 정보
   * @return 요청 결과
   */
  @PostMapping(value = "/constellation")
  public ResponseEntity<?> postAsConstellation(@RequestBody UserConstellationCreateRequest param) {
    UserValidator.validate(param);

    userConstellationCommandService.create(param);
    return ResponseEntity.ok(new SuccessResponse());
  }

  @PostMapping(value = "/deleteAccountReason")
  public ResponseEntity<?> postUserOutReason(@RequestBody UserOutReasonRequest param) {
    deleteAccountReasonCommandService.saveDeleteAccountReason(param);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }
}
