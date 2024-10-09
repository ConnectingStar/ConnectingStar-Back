package connectingstar.tars.habit.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


/**
 * 휴식 기록 저장 요청
 *
 * @author 이우진
 * @link <a href="https://www.figma.com/design/deVOGLOqzbCjKJP9fDeB3i/%ED%95%B4%EB%B9%97%EB%B2%84%EB%94%94?node-id=4509-17153&t=DHr54WaoOqEj05dm-4">Figma - 실천 기록</a>
 * @deprecated use {@link HistoryRestPostRequest} instead
 */
@Getter
@Setter
@Deprecated
public class HabitHistoryRestPostRequest {
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
     * 느낀점.
     * 별자취 남기기
     */
    private String review;
}