package connectingstar.tars.habit.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import connectingstar.tars.common.domain.EntityReference;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 습관 기록 생성 후 response DTO
 *
 * @author 이우진
 */
@Getter
public class HabitHistoryPostResponse {
    /**
     * 생성된 습관 기록 ID
     */
    private Integer habitHistoryId;

    /**
     * 기록을 생성한 사용자 ID
     */
    private EntityReference user;

    /**
     * 습관 ID
     */
    private EntityReference runHabit;

    /**
     * 실천한 날짜, 시간
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
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
