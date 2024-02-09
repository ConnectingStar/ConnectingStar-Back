package connectingstar.tars.user.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String nickname; //닉네임

    @Enumerated(EnumType.STRING)
    private String gender; //남(M),여(W),선택안함(N)

    private String ageRange; //나이 연령대

    private String referrer; //유입 경로

    private String identity; //정체성

    private String email; //소셜 로그인 이메일

    private String profileCharacter; //프로필 캐릭터

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Builder
    public User(String nickname, String gender, String ageRange, String referrer, String identity,
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
}
