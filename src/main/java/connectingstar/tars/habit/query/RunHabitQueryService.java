package connectingstar.tars.habit.query;

import connectingstar.tars.habit.domain.RunHabit;
import connectingstar.tars.habit.repository.RunHabitDao;
import connectingstar.tars.habit.repository.RunHabitRepository;
import connectingstar.tars.habit.response.RunPutResponse;
import connectingstar.tars.user.command.UserHabitCommandService;
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

    private final RunHabitDao runHabitDao;
    private final RunHabitRepository runHabitRepository;
    private final UserHabitCommandService userHabitCommandService;

    /**
     * 진행중인 습관 목록 조회
     *
     * @return 진행중인 습관 수정을 위한 사용자 PK, 정체성, 실천 시간, 장소, 행동, 얼마나, 단위, 1차 알림시각, 2차 알림시각 (추후 고치겠습니다 지금 시간이 없어서 ㅠ)
     * TODO: 리턴값 수정
     */
    public List<RunPutResponse> getList() {

        List<RunHabit> allByUser = runHabitRepository.findAllByUser(userHabitCommandService.findUserByUserId());
        return allByUser.stream().map(RunPutResponse::new).collect(Collectors.toList());
    }

}
