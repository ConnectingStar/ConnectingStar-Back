package connectingstar.tars.history.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 습관 기록 단일 정보를 저장하는 DTO.
 * 관련 엔티티 정보는 id만 포함.
 *
 * @author 이우진
 */
@Getter
@Setter
public class HabitHistoryDto {
    /**
     * 생성된 습관 기록 ID
     */
    private Integer habitHistoryId;

    /**
     * 기록을 생성한 사용자 ID
     */
    private Integer userId;

    /**
     * 습관 ID
     */
    private Integer runHabitId;

    /**
     * 실천한 날짜, 시간
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime runDate;

    /**
     * 실천한 장소
     */
    private String runPlace;

    /**
     * 액션
     * (책 읽기를)
     */
    private String action;

    /**
     * 실천량
     */
    private Integer runValue;

    /**
     * 만족도
     * 표정 이미지로 표현됨
     */
    private Integer achievement;

    /**
     * 느낀점
     */
    private String review;

    /**
     * 휴식 여부
     */
    private Boolean isRest;
}
