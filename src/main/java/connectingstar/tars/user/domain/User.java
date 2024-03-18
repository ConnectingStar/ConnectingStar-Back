package connectingstar.tars.user.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import connectingstar.tars.habit.domain.HabitHistory;
import connectingstar.tars.habit.domain.QuitHabit;
import connectingstar.tars.habit.domain.RunHabit;
import connectingstar.tars.star.domain.Star;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String nickname; //닉네임

    @Enumerated(EnumType.STRING)
    private Gender gender; //남(M),여(W),선택안함(N)

    private String ageRange; //나이 연령대

    private String referrer; //유입 경로

    private String identity; //정체성

    private String email; //소셜 로그인 이메일

    private String profileCharacter; //프로필 캐릭터

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String socialId;

    private String refreshToken;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<HabitHistory> habitHistories = new ArrayList<>(); //사용자의 습관기록 데이터들

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<RunHabit> runHabits = new ArrayList<>(); //사용자가 진행중인 습관들

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<QuitHabit> quitHabits = new ArrayList<>() ; //사용자가 이전에 종료한 습관들

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,CascadeType.MERGE} )
    @JoinColumn(name = "star_id", referencedColumnName = "id")
    private Star star; //사용자가 가지고 있는 별


    /**
     * 보유한 별자리(캐릭터) 목록
     */
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private final List<UserConstellation> userConstellationList = new ArrayList<>();

    @Builder
    public User(String nickname, Gender gender, String ageRange, String referrer, String identity,
                String email, String profileCharacter, SocialType socialType, Role role) {
        this.nickname = nickname;
        this.gender = gender;
        this.ageRange = ageRange;
        this.referrer = referrer;
        this.identity = identity;
        this.email = email;
        this.profileCharacter = profileCharacter;
        this.socialType = socialType;
        this.role = role;
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
     * 리프레쉬 토큰 업데이트
     */

    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }
}
