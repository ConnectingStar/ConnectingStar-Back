package connectingstar.tars.habit.query;

import static connectingstar.tars.common.exception.errorcode.HabitErrorCode.RUN_HABIT_NOT_FOUND;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.habit.domain.RunHabit;
import connectingstar.tars.habit.repository.RunHabitRepository;
import connectingstar.tars.habit.request.RunGetRequest;
import connectingstar.tars.habit.response.RunGetListResponse;
import connectingstar.tars.habit.response.RunPutResponse;
import connectingstar.tars.user.command.UserHabitCommandService;
import connectingstar.tars.user.domain.User;
import java.util.Objects;
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

}
