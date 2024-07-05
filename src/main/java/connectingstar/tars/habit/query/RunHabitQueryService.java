package connectingstar.tars.habit.query;

import static connectingstar.tars.common.exception.errorcode.HabitErrorCode.RUN_HABIT_NOT_FOUND;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.device.domain.Device;
import connectingstar.tars.habit.domain.HabitHistory;
import connectingstar.tars.habit.domain.RunHabit;
import connectingstar.tars.habit.dto.RunHabitWithDevice;
import connectingstar.tars.habit.repository.HabitHistoryRepository;
import connectingstar.tars.habit.repository.RunHabitDao;
import connectingstar.tars.habit.repository.RunHabitRepository;
import connectingstar.tars.habit.request.RunDayGetRequest;
import connectingstar.tars.habit.request.RunGetRequest;
import connectingstar.tars.habit.response.HabitHistoryWeekelyWriteResponse;
import connectingstar.tars.habit.response.HabitDayGetResponse;
import connectingstar.tars.habit.response.RunGetListResponse;
import connectingstar.tars.habit.response.RunPutResponse;
import connectingstar.tars.pushnotification.dto.PushNotificationMessage;
import connectingstar.tars.user.command.UserHabitCommandService;
import connectingstar.tars.user.domain.User;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

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

    public List<HabitDayGetResponse> getDay(RunDayGetRequest param) {
        //이전 일자에 대해선 오늘기준으로 월~ 일의 데이터를 전달 -> 모든 습관이 존재할경우 true, false
        //오늘의 습관 다 꺼내옴 -> 만약 해빗 히스토리가 없는 경우 -> 기록가능 ->0
        User userByUserId = userHabitCommandService.findUserByUserId();
        return userByUserId.getRunHabits()
            .stream()
            .map(rh -> getDayResponse(param.getReferenceDate(), rh)).toList();
    }
    private static @NotNull HabitDayGetResponse getDayResponse(LocalDate referenceDate, RunHabit runHabit) {
        Optional<HabitHistory> first = runHabit.getHabitHistories().stream()
            .filter(hh -> hh.getRunDate().toLocalDate().isEqual(referenceDate)).findFirst();
        Integer habitStatus = 0;
        if(first.isEmpty()) {
            //해빗 히스토리가 없는 경우 -> 오늘의 날짜와 조회한 date가 2일 이상 차이나는 경우 -> 기록 불가 -> 1
            if (referenceDate.isBefore(LocalDate.now().minusDays(2))) {
                habitStatus = 1;
                return new HabitDayGetResponse(runHabit, habitStatus);
            }
            return new HabitDayGetResponse(runHabit, habitStatus);
        }
        // 해빗 히스토리가 있는 경우 -> 만약 습관 status? 가 0이 아닐경우 -> 오늘 실천 완료 -> 2
        HabitHistory habitHistory = first.get();
        if(habitHistory.getAchievement() != 0 ){
            habitStatus = 2;
            return new HabitDayGetResponse(runHabit, habitStatus);
        }
        // 해빗 히스토리가 있는경우 -> status가 0일경우  -> 오늘 휴식 -> 3
        return new HabitDayGetResponse(runHabit, habitStatus);
    }

    public List<HabitHistoryWeekelyWriteResponse> getHistoryTotalWrite(RunDayGetRequest param){
        User userByUserId = userHabitCommandService.findUserByUserId();
        int size = userByUserId.getRunHabits().size();
        LocalDate referenceDate = param.getReferenceDate();
        LocalDate thisWeekSunday = referenceDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDate startDay = thisWeekSunday.atStartOfDay().toLocalDate();
        List<HabitHistoryWeekelyWriteResponse> responses = new ArrayList<>();
        int count = 1;
      while (count <= 7) {
          HabitHistoryWeekelyWriteResponse habitHistoryWeekelyWriteResponse = new HabitHistoryWeekelyWriteResponse(startDay,
              checkTodayAllHabitHistoryCreate(startDay, userByUserId, size));
          responses.add(habitHistoryWeekelyWriteResponse);
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

    /**
     * 유저의 수행 시각이 1일 전인 기록이 존재하지 않는 습관을 fetch 합니다.
     * runDate가 어제인 history가 없는 습관 fetch.
     *
     * @author 이우진
     */
    public List<RunHabitWithDevice> getListByYesterdayHistoryNotExistWithDevice() {
        LocalDate yesterday = LocalDate.now().minusDays(1);

        return runHabitRepository.findAllByHistoryOfRunDateNotExistWithDevice(yesterday);
    }

    /**
     * 기록 독려 알림 메세지를 생성합니다
     *
     * @link <a href="https://www.figma.com/design/deVOGLOqzbCjKJP9fDeB3i/%ED%95%B4%EB%B9%97%EB%B2%84%EB%94%94?node-id=4076-4527&m=dev">Figma</a>
     */
    public PushNotificationMessage generateMissingHistoryPushNotificationMessage(RunHabit runHabit, Device device) {
        return PushNotificationMessage.builder()
                .token(device.getFcmRegistrationToken())
                .title(runHabit.getAction() + " 기록 리마인더\n")
                .body("앗,, 어제 "+ runHabit.getAction() +" 기록이 없네요\uD83D\uDE25\n" +
                        "마감(자정) 전에 남기고 \"" + runHabit.getIdentity() +"\" 강화하기!")
                .build();
    }
}
