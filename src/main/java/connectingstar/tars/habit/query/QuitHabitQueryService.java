package connectingstar.tars.habit.query;

import com.querydsl.core.types.Order;
import connectingstar.tars.habit.domain.QuitHabit;
import connectingstar.tars.habit.mapper.QuitHabitMapper;
import connectingstar.tars.habit.repository.QuitHabitRepositoryCustom;
import connectingstar.tars.habit.request.QuitHabitGetListRequestParam;
import connectingstar.tars.habit.response.QuitHabitGetListResponse;
import connectingstar.tars.habit.response.QuitListResponse;
import connectingstar.tars.user.domain.User;
import connectingstar.tars.user.query.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 종료 습관 조회 서비스
 *
 * @author 김성수
 */

@Service
@RequiredArgsConstructor
public class QuitHabitQueryService {

    private final QuitHabitRepositoryCustom quitHabitRepositoryCustom;

    private final UserQueryService userQueryService;
    private final ConversionService conversionService;

    private final QuitHabitMapper quitHabitMapper;

    public QuitHabitGetListResponse getMyList(QuitHabitGetListRequestParam requestParam) {
        User currentUser = userQueryService.getCurrentUserOrElseThrow();

        List<QuitHabit> quitHabits = quitHabitRepositoryCustom
                .findByUserId(currentUser.getId(),
                        requestParam.getPage(),
                        requestParam.getSize(),
                        requestParam.getSortBy(),
                        conversionService.convert(requestParam.getOrder(), Order.class)
                );

        return QuitHabitGetListResponse.builder()
                .quitHabits(quitHabitMapper.toDtoList(quitHabits))
                .build();
    }

    /**
     * 종료 습관 목록 조회
     *
     * @return 배열(종료한 습관 ID, 사용자 PK, 사용자 이름, 실천 시간, 장소, 행동, 실천횟수, 휴식 실천횟수, 종료 사유, 시작 날짜, 종료 날짜)
     * @deprecated use {@link #getMyList(QuitHabitGetListRequestParam)}
     */
    @Deprecated
    public List<QuitListResponse> getList() {
        return quitHabitRepositoryCustom.getList();
    }
}