package connectingstar.tars.habit.query;

import connectingstar.tars.habit.domain.RunHabit;
import connectingstar.tars.habit.repository.RunHabitRepository;
import connectingstar.tars.habit.response.HabitGetOneStatisticsResponse;
import connectingstar.tars.history.command.HabitHistoryCommandService;
import connectingstar.tars.history.domain.HabitHistory;
import connectingstar.tars.user.domain.User;
import connectingstar.tars.user.query.UserQueryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RunHabitQueryServiceTest {

    @Mock
    private UserQueryService userQueryService;

    @Mock
    private RunHabitRepository runHabitRepository;

    @Mock
    private HabitHistoryCommandService habitHistoryCommandService;

    @InjectMocks
    private RunHabitQueryService runHabitQueryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getMyStatisticsByIdReturnsCorrectStatistics() {
        User user = mock(User.class);
        Integer runHabitId = 1;
        RunHabit runHabit = mock(RunHabit.class);
        HabitHistory habitHistory1 = mock(HabitHistory.class);
        HabitHistory habitHistory2 = mock(HabitHistory.class);
        HabitHistory restHistory = mock(HabitHistory.class);

        when(userQueryService.getCurrentUserOrElseThrow()).thenReturn(user);
        when(runHabitRepository.findByRunHabitId(runHabitId)).thenReturn(Optional.ofNullable(runHabit));
        when(runHabit.getUser()).thenReturn(user);
        when(user.getId()).thenReturn(1);

        when(runHabit.getHabitHistories()).thenReturn(List.of(habitHistory1, habitHistory2));
        when(habitHistory1.getIsRest()).thenReturn(false);
        when(habitHistory2.getIsRest()).thenReturn(false);
        when(restHistory.getIsRest()).thenReturn(true);
        when(habitHistory1.getRunValue()).thenReturn(10);
        when(habitHistory2.getRunValue()).thenReturn(20);
        when(restHistory.getRunValue()).thenReturn(null);

        HabitGetOneStatisticsResponse response = runHabitQueryService.getMyStatisticsById(runHabitId);

        // completed: 2, rest: 1
        assertEquals(2 * 1 + 0 * 1, response.getTotalStarCount());
        assertEquals(10 + 20, response.getTotalValue());
    }

    @Test
    void getMyStatisticsByIdReturnsZeroWhenNoHistories() {
        User user = mock(User.class);
        Integer runHabitId = 1;
        RunHabit runHabit = mock(RunHabit.class);

        when(userQueryService.getCurrentUserOrElseThrow()).thenReturn(user);
        when(runHabitRepository.findByRunHabitId(runHabitId)).thenReturn(Optional.ofNullable(runHabit));
        when(runHabit.getUser()).thenReturn(user);
        when(user.getId()).thenReturn(1);

        when(runHabit.getHabitHistories()).thenReturn(List.of());

        HabitGetOneStatisticsResponse response = runHabitQueryService.getMyStatisticsById(runHabitId);

        assertEquals(0, response.getTotalStarCount());
        assertEquals(0, response.getTotalValue());
    }
}