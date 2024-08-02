package connectingstar.tars.user.domain;

import connectingstar.tars.common.domain.BaseTimeEntity;
import connectingstar.tars.constellation.domain.Constellation;
import connectingstar.tars.habit.domain.QuitHabit;
import connectingstar.tars.habit.domain.RunHabit;
import connectingstar.tars.history.domain.HabitHistory;
import connectingstar.tars.oauth.domain.enums.SocialType;
import connectingstar.tars.user.domain.enums.AgeRangeType;
import connectingstar.tars.user.domain.enums.GenderType;
import connectingstar.tars.user.request.UserOnboardingRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
     * 이메일
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
    @Convert(converter = AgeRangeType.TypeCodeConverter.class)
    @Column(name = "age_range")
    private AgeRangeType ageRange;
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
     * 온보딩 통과 여부
     */
    @Column(name = "onboard")
    private Boolean onboard = false;
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
    /**
     * 성별 타입
     */
    @Convert(converter = GenderType.TypeCodeConverter.class)
    @Column(name = "gender")
    private GenderType gender;

    ///////////////////////////////////////////////////////////
    // Relations
    ///////////////////////////////////////////////////////////
    /**
     * 프로필로 설정한 별자리
     * 유저가 보유(완성)한 버디 중 하나 선택.
     *
     * <a href="https://www.figma.com/design/deVOGLOqzbCjKJP9fDeB3i/%ED%95%B4%EB%B9%97%EB%B2%84%EB%94%94?node-id=10014-14243&t=1I8FE80hXdeDG0fl-4">Figma - 마이 페이지</a>
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "constellation_id")
    private Constellation constellation;
    /**
     * 습관 기록 리스트
     */
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<HabitHistory> habitHistories = new ArrayList<>();
    /**
     * 진행중인 습관 리스트
     */
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<RunHabit> runHabits = new ArrayList<>();
    /**
     * 종료한 습관 리스트
     */
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<QuitHabit> quitHabits = new ArrayList<>();
    /**
     * 보유한 별자리(캐릭터) 목록
     */
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private final List<UserConstellation> userConstellationList = new ArrayList<>();

    public User(String email, SocialType socialType) {
        this.email = email;
        this.socialType = socialType;
    }

    ///////////////////////////////////////////////////////////
    // Method
    ///////////////////////////////////////////////////////////

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
     * 온보딩 데이터 저장
     */
    public void updateOnboarding(UserOnboardingRequest onboardingRequest) {
        this.nickname = onboardingRequest.getNickname();
        this.gender = GenderType.fromCode(onboardingRequest.getGenderType());
        this.ageRange = AgeRangeType.fromCode(onboardingRequest.getAgeRangeType());
        this.referrer = onboardingRequest.getReferrer();
        this.onboard = true;
    }


    /**
     * 프로필 별자리 수정
     */
    public void updateConstellation(Constellation constellation) {
        this.constellation = constellation;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateIdentity(String identity) {
        this.identity = identity;
    }

    public void updateGender(GenderType genderType) {
        this.gender = genderType;
    }

    public void updateAgeRange(AgeRangeType ageRangeType) {
        this.ageRange = ageRangeType;
    }

    /**
     * 별 사용
     */
    public void updateStar() {
        this.star -= 1;
    }

    public void updateReferrer(String referrer) {
        this.referrer = referrer;
    }

    public void updateOnboard(boolean onboard) {
        this.onboard = onboard;
    }
}
