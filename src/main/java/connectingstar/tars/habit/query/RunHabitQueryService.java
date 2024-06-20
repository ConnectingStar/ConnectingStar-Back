package connectingstar.tars.habit.query;

import static connectingstar.tars.common.exception.errorcode.HabitErrorCode.RUN_HABIT_NOT_FOUND;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.habit.domain.HabitHistory;
import connectingstar.tars.habit.domain.RunHabit;
import connectingstar.tars.habit.repository.HabitHistoryRepository;
import connectingstar.tars.habit.repository.RunHabitDao;
import connectingstar.tars.habit.repository.RunHabitRepository;
import connectingstar.tars.habit.request.RunGetRequest;
import connectingstar.tars.habit.response.HabitGetHome2Response;
import connectingstar.tars.habit.response.HabitGetHomeResponse;
import connectingstar.tars.habit.response.RunGetListResponse;
import connectingstar.tars.habit.response.RunPutResponse;
import connectingstar.tars.user.command.UserHabitCommandService;
import connectingstar.tars.user.domain.User;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 진행중인 습관 조회 서비스
 *
 * @author 김성수
 */

@Service
@RequiredArgsConstructor
public class RunHabitQueryService {

    private final RunHabitRepository runHabitRepository;
    private final HabitHistoryRepository habitHistoryRepository;
    private final RunHabitDao runHabitDao;
    private final UserHabitCommandService userHabitCommandService;

    /**
     * 진행중인 습관 목록 조회
     *
     * @return 진행중인 습관 수정을 위한 사용자 PK, 정체성, 실천 시간, 장소, 행동, 얼마나, 단위, 1차 알림시각, 2차 알림시각 (추후 고치겠습니다 지금 시간이 없어서 ㅠ)
     * TODO: 리턴값 수정
     */
    public List<RunGetListResponse> getList() {

        List<RunHabit> allByUser = runHabitRepository.findAllByUser(userHabitCommandService.findUserByUserId());
        return allByUser.stream().map(RunGetListResponse::new).collect(Collectors.toList());
    }

    /**
     * 진행중인 습관 조회
     *
     * @return 진행중인 습관 수정을 위한 사용자 PK, 정체성, 실천 시간, 장소, 행동, 얼마나, 단위, 1차 알림시각, 2차 알림시각 (추후 고치겠습니다 지금 시간이 없어서 ㅠ)
     * TODO: 리턴값 수정
     */
    public RunPutResponse get(RunGetRequest param) {

        User userByUserId = userHabitCommandService.findUserByUserId();
        return userByUserId.getRunHabits()
            .stream()
            .filter(rh -> Objects.equals(rh.getRunHabitId(), param.getRunHabitId()))
            .map(RunPutResponse::new)
            .findFirst().orElseThrow(() -> new ValidationException(RUN_HABIT_NOT_FOUND));
    }

    public HabitGetHomeResponse getDay(RunGetRequest param) {
        //이전 일자에 대해선 오늘기준으로 월~ 일의 데이터를 전달 -> 모든 습관이 존재할경우 true, false
        //오늘의 습관 다 꺼내옴 -> 만약 해빗 히스토리가 없는 경우 -> 기록가능 ->0
        User userByUserId = userHabitCommandService.findUserByUserId();
        RunHabit runHabit = userByUserId.getRunHabits()
            .stream()
            .filter(rh -> rh.getRunHabitId().equals(param.getRunHabitId())).findFirst()
            .orElseThrow(() -> new ValidationException(RUN_HABIT_NOT_FOUND));
        Optional<HabitHistory> first = runHabit.getHabitHistories().stream()
            .filter(hh -> hh.getRunDate().toLocalDate().isEqual(param.getReferenceDate())).findFirst();
        Integer habitStatus = 0;
        if(first.isEmpty()) {
            //해빗 히스토리가 없는 경우 -> 오늘의 날짜와 조회한 date가 2일 이상 차이나는 경우 -> 기록 불가 -> 1
            if (param.getReferenceDate().isBefore(LocalDate.now().minusDays(2))) {
                habitStatus = 1;
                return new HabitGetHomeResponse(runHabit, habitStatus);
            }
            return new HabitGetHomeResponse(runHabit, habitStatus);
        }
        // 해빗 히스토리가 있는 경우 -> 만약 습관 status? 가 0이 아닐경우 -> 오늘 실천 완료 -> 2
        HabitHistory habitHistory = first.get();
        if(habitHistory.getAchievement() != 0 ){
            habitStatus = 2;
            return new HabitGetHomeResponse(runHabit,habitStatus);
        }
        // 해빗 히스토리가 있는경우 -> status가 0일경우  -> 오늘 휴식 -> 3
        return new HabitGetHomeResponse(runHabit, habitStatus);
    }

    public List<HabitGetHome2Response> getHistoryTotalWrite(RunGetRequest param){
        User userByUserId = userHabitCommandService.findUserByUserId();
        int size = userByUserId.getRunHabits().size();
        LocalDate referenceDate = param.getReferenceDate();
        LocalDate thisWeekSunday = referenceDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDate startDay = thisWeekSunday.atStartOfDay().toLocalDate();
        List<HabitGetHome2Response> responses = new ArrayList<>();
        int count = 1;
      while (count <= 7) {
          HabitGetHome2Response habitGetHome2Response = new HabitGetHome2Response(startDay,
              checkTodayAllHabitHistoryCreate(startDay, userByUserId, size));
          responses.add(habitGetHome2Response);
          startDay = startDay.plusDays(1);
          count++;
      }
      return responses;
    }

    private boolean checkTodayAllHabitHistoryCreate(LocalDate referenceDate, User user, Integer runHabitSize){
        List<RunHabit> list = user.getRunHabits().stream()
            .filter(rh -> rh.getHabitHistories().stream().anyMatch(hh -> hh.getRunDate().toLocalDate().isEqual(referenceDate))).toList();
        return list.size() == runHabitSize;
    }
}
