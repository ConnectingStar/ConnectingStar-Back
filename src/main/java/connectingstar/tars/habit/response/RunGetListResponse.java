package connectingstar.tars.habit.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import connectingstar.tars.common.domain.TimeInfo;
import connectingstar.tars.habit.domain.RunHabit;
import connectingstar.tars.history.domain.HabitHistory;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.List;


/**
 * 진행중인 습관 수정 응답
 *
 * @author 김성수
 * @deprecated us {@link HabitGetListResponse} instead
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"runHabitId", "userId", "userNickname", "identity", "runTime", "place", "behavior", "behaviorValue", "behaviorUnit", "historyInfo"})
public class RunGetListResponse {

    /**
     * 진행중인 습관 ID
     */
    private final Integer runHabitId;

    /**
     * 사용자 PK
     */
    private final Integer userId;


    /**
     * 사용자 이름
     */
    private final String userNickname;

    /**
     * 정체성
     */
    private final String identity;

    /**
     * 실천 시간
     */
    private final TimeInfo runTime;

    /**
     * 장소
     */
    private final String place;

    /**
     * 행동
     */
    private final String behavior;

    /**
     * 얼마나
     */
    private final Integer behaviorValue;

    /**
     * 단위
     */
    private final String behaviorUnit;

    /**
     * 습관 기록 정보 (일주일)
     */
    private final List<HistoryInfo> historyInfo;

    private @NotNull List<HistoryInfo> getList(RunHabit runHabit) {
        return runHabit.getHabitHistories()
                .stream()
                .filter(hh -> hh.getRunDate().toLocalDate().isAfter(LocalDate.now().minusDays(7)))
                .map(HistoryInfo::new)
                .toList();
    }

    public RunGetListResponse(RunHabit runHabit) {
        this.runHabitId = runHabit.getRunHabitId();
        this.userId = runHabit.getUser().getId();
        this.userNickname = runHabit.getUser().getNickname();
        this.identity = runHabit.getIdentity();
        this.runTime = new TimeInfo(runHabit.getRunTime());
        this.place = runHabit.getPlace();
        this.behavior = runHabit.getAction();
        this.behaviorValue = runHabit.getValue();
        this.behaviorUnit = runHabit.getUnit();
        this.historyInfo = getList(runHabit);
    }

    @Getter
    public static class HistoryInfo {
        private final LocalDate runDate;

        /**
         * history.achievement
         * 휴식 여부 구분 위해 사용
         **/
        private final Integer status;


        public HistoryInfo(HabitHistory habitHistory) {
            this.runDate = habitHistory.getRunDate().toLocalDate();
            this.status = habitHistory.getAchievement();
        }
    }
}
