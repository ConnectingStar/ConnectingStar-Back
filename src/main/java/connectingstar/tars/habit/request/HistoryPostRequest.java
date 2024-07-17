package connectingstar.tars.habit.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;


/**
 * 오늘 실천한 습관 기록을 저장 요청
 *
 * @link <a href="https://www.figma.com/design/deVOGLOqzbCjKJP9fDeB3i/%ED%95%B4%EB%B9%97%EB%B2%84%EB%94%94?node-id=4509-17153&t=DHr54WaoOqEj05dm-4">Figma - 실천 기록</a>
 * @author 김성수, 이우진
 */
@Getter
@Setter
public class HistoryPostRequest {


    /**
     * 진행중인 습관 ID
     */
    private Integer runHabitId;

    /**
     * 생성 기준 날짜
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate referenceDate;

    /**
     * 실천 시간
     * (오후 n시에)
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime runTime;

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
    private Integer behaviorValue;

    /**
     * 만족도
     */
    private Integer achievement;

    /**
     * 느낀점.
     * 발자취 남기기
     */
    private String review;
}