package connectingstar.tars.common.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 회원 관련 에러 코드
 *
 * @author 송병선
 */
@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {


    USER_PARAM_ID_EMPTY(HttpStatus.BAD_REQUEST, "회원 ID는 필수 입력값입니다."),
    USER_PARAM_NICKNAME_EMPTY(HttpStatus.BAD_REQUEST, "닉네임은 필수 입력값입니다."),
    USER_PARAM_IDENTITY_EMPTY(HttpStatus.BAD_REQUEST, "정체성은 필수 입력값입니다."),
    USER_PARAM_GENDER_TYPE_EMPTY(HttpStatus.BAD_REQUEST, "성별 타입은 필수 입력값입니다."),
    USER_PARAM_GENDER_TYPE_INVALID(HttpStatus.BAD_REQUEST, "유효하지 않은 성별 타입입니다."),
    USER_PARAM_AGE_RANGE_TYPE_EMPTY(HttpStatus.BAD_REQUEST, "나이대 타입은 필수 입력값입니다."),
    USER_PARAM_AGE_RANGE_TYPE_INVALID(HttpStatus.BAD_REQUEST, "유효하지 않은 나이대 타입입니다."),

    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "회원 정보를 찾을 수 없습니다."),
    USER_IDENTITY_NOT_FOUNT(HttpStatus.BAD_REQUEST, "대표 정체성을 가진 습관을 찾을 수 없습니다."),

    USER_CONSTELLATION_DUPLICATE(HttpStatus.BAD_REQUEST, "이미 등록된 별자리입니다."),
    USER_UNLOCKING_CONSTELLATION_ALREADY_IN_PROGRESS(HttpStatus.BAD_REQUEST, "이미 진행중인 별자리가 존재합니다."),
    USER_CONSTELLATION_PROGRESS_NOT_EXIST(HttpStatus.BAD_REQUEST, "진행중인 별자리가 없습니다."),
    USER_CONSTELLATION_NOT_REGISTER(HttpStatus.BAD_REQUEST, "해당 별자리를 보유하고 있지 않습니다."),

    USER_DEFAULT_CONSTELLATION_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "DB에서 기본 별자리를 찾을 수 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
