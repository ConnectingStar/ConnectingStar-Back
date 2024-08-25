package connectingstar.tars.habit.command;

import connectingstar.tars.habit.domain.QuitHabit;
import connectingstar.tars.habit.dto.QuitHabitDto;
import connectingstar.tars.habit.mapper.QuitHabitMapper;
import connectingstar.tars.habit.query.QuitHabitQueryService;
import connectingstar.tars.habit.repository.QuitHabitRepository;
import connectingstar.tars.habit.response.QuitHabitDeleteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuitHabitCommandService {
    private final QuitHabitRepository quitHabitRepository;

    private final QuitHabitQueryService quitHabitQueryService;

    private final QuitHabitMapper quitHabitMapper;

    /**
     * 종료된 습관 삭제.
     * <p>
     * 현재 로그인한 사용자의 종료된 습관을 삭제합니다.
     * 입력된 습관이 현재 사용자의 소유가 아니면 예외를 발생시킵니다.
     */
    public QuitHabitDeleteResponse deleteMineById(Integer quitHabitId) {
        QuitHabit quitHabit = quitHabitQueryService.getMineByIdOrElseThrow(quitHabitId);

        // 삭제 전 dto 생성
        QuitHabitDto deletedQuitHabit = quitHabitMapper.toDto(quitHabit);

        quitHabitRepository.delete(quitHabit);

        return QuitHabitDeleteResponse.builder()
                .quitHabit(deletedQuitHabit)
                .build();
    }
}
