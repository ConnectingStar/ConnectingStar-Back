package connectingstar.tars.common.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 별자리(캐릭터) 관련 에러 코드
 *
 * @author 송병선
 */
@Getter
@RequiredArgsConstructor
public enum ConstellationErrorCode implements ErrorCode {

    CONSTELLATION_PARAM_NAME_EMPTY(HttpStatus.BAD_REQUEST, "별자리 이름은 필수 입력값입니다."),
    CONSTELLATION_PARAM_DESCRIPTION_EMPTY(HttpStatus.BAD_REQUEST, "별자리 설명은 필수 입력값입니다."),
    CONSTELLATION_PARAM_STORY_EMPTY(HttpStatus.BAD_REQUEST, "별자리 스토리는 필수 입력값입니다."),
    CONSTELLATION_PARAM_IDENTITY_EMPTY(HttpStatus.BAD_REQUEST, "별자리 정체성은 필수 입력값입니다."),
    CONSTELLATION_PARAM_IMAGE_EMPTY(HttpStatus.BAD_REQUEST, "별자리 이미지는 필수 입력값입니다."),
    CONSTELLATION_PARAM_CHARACTER_IMAGE_EMPTY(HttpStatus.BAD_REQUEST, "캐릭터 이미지는 필수 입력값입니다."),
    CONSTELLATION_PARAM_STAR_COUNT_EMPTY(HttpStatus.BAD_REQUEST, "필요한 별 개수는 필수 입력값입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
