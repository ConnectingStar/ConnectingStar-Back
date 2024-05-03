package connectingstar.tars.user.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import connectingstar.tars.habit.domain.RunHabit;
import java.util.List;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserBasicInfoAndHabitResponse {

  String nickname;
  String identity;
  String profileCharacter;
  List<RunHabit> runHabitList;

  public UserBasicInfoAndHabitResponse(String nickname, String identity
      //, String profileCharacter
      , List<RunHabit> runHabitList) {
    this.nickname = nickname;
    this.identity = identity;
    //this.profileCharacter = profileCharacter;
    this.runHabitList = runHabitList;
  }
}
