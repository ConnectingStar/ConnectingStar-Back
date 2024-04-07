package connectingstar.tars.user.domain;

import connectingstar.tars.common.domain.BaseTimeEntity;
import connectingstar.tars.habit.domain.HabitHistory;
import connectingstar.tars.habit.domain.QuitHabit;
import connectingstar.tars.habit.domain.RunHabit;
import connectingstar.tars.oauth.domain.enums.SocialType;
import connectingstar.tars.user.domain.enums.GenderType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원 엔티티
 *
 * @author 송병선
 */
@Entity
@Getter
@NoArgsConstructor
public class User extends BaseTimeEntity {

  /**
   * 회원 ID
   */
  @Id
  @Column(name = "user_id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  /**
   * 회원 ID
   */
  @Column(name = "email", nullable = false)
  private String email;
  /**
   * 닉네임
   */
  @Column(name = "nickname")
  private String nickname;
  /**
   * 연령대
   */
  @Column(name = "ageRange")
  private String ageRange;
  /**
   * 성별 타입
   */
  @Convert(converter = GenderType.TypeCodeConverter.class)
  @Column(name = "gender_type")
  private final GenderType genderType = GenderType.NONE;
  /**
   * 정체성
   */
  @Column(name = "identity")
  private String identity;
  /**
   * 유입 경로
   */
  @Column(name = "referrer")
  private String referrer;
  /**
   * 프로필 캐릭터
   */
  @Column(name = "profile_character")
  private String profileCharacter;
  /**
   * 보유 별 개수
   */
  @Column(name = "star", nullable = false)
  private Integer star = 0;
  /**
   * Resource Server 타입
   */
  @Convert(converter = SocialType.TypeCodeConverter.class)
  @Column(name = "social_type", nullable = false)
  private SocialType socialType;

  ///////////////////////////////////////////////////////////
  // Relations
  ///////////////////////////////////////////////////////////
  /**
   * 습관 기록 리스트
   */
  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,
      CascadeType.MERGE})
  private List<HabitHistory> habitHistories = new ArrayList<>();
  /**
   * 진행중인 습관 리스트
   */
  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,
      CascadeType.MERGE})
  private List<RunHabit> runHabits = new ArrayList<>();
  /**
   * 종료한 습관 리스트
   */
  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,
      CascadeType.MERGE})
  private List<QuitHabit> quitHabits = new ArrayList<>();
  /**
   * 보유한 별자리(캐릭터) 목록
   */
  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,
      CascadeType.MERGE})
  private final List<UserConstellation> userConstellationList = new ArrayList<>();

  public User(String email, SocialType socialType) {
    this.email = email;
    this.socialType = socialType;
  }

  /**
   * 별자리(캐릭터) 추가
   */
  public void addUserConstellation(UserConstellation userConstellation) {
    if (Objects.isNull(userConstellation)) {
      return;
    }

    this.userConstellationList.add(userConstellation);
    userConstellation.setUser(this);
  }

  /**
   * 별 사용
   */
  public void updateStar() {
    this.star -= 1;
  }
}
