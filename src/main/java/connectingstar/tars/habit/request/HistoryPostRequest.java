package connectingstar.tars.habit.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


/**
 * 오늘 실천한 습관을 저장 요청
 *
 * @author 김성수
 */
@Getter
@Setter
public class HistoryPostRequest {


    /**
     * 진행중인 습관 ID
     */
    private Integer runHabitId;

    /**
     * 만족도
     */
    private Integer achievement;

    /**
     * 실천한 장소
     */
    private String runPlace;

    /**
     * 생성 기준 날짜
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate referenceDate;

    /**
     * 실천량
     */
    private Integer behaviorValue;

    /**
     * 느낀점
     */
    private String review;
}