package connectingstar.tars.user.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import connectingstar.tars.habit.domain.RunHabit;
import lombok.Getter;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserBasicInfoAndHabitResponse {
    String nickname;
    String identity;
    List<RunHabit> runHabitList;

    public UserBasicInfoAndHabitResponse(String nickname, String identity, List<RunHabit> runHabitList) {
        this.nickname = nickname;
        this.identity = identity;
        this.runHabitList = runHabitList;
    }
}
