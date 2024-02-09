package connectingstar.tars.user.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    /**
     * 보유한 별자리(캐릭터) 목록
     */
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private final List<UserConstellation> userConstellationList = new ArrayList<>();

    @Builder
    public User(String nickname, Gender gender, String ageRange, String referrer, String identity,
                String email, String profileCharacter, SocialType socialType) {
        this.nickname = nickname;
        this.gender = gender;
        this.ageRange = ageRange;
        this.referrer = referrer;
        this.identity = identity;
        this.email = email;
        this.profileCharacter = profileCharacter;
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
}
