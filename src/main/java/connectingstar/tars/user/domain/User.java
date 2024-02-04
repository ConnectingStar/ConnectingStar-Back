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

    private String gender; //남,여,선택안함

    private String ageRange; //나이 연령대

    private String referrer; //유입 경로

    private String identity; //정체성

    private String email; //

    private String password;

    private String profileCharacter;

    @Enumerated
    private SocialType socialType;

    @Builder
    public User(String nickname, String gender, String ageRange, String referrer, String identity,
                String email, String password, String profileCharacter, SocialType socialType) {
        this.nickname = nickname;
        this.gender = gender;
        this.ageRange = ageRange;
        this.referrer = referrer;
        this.identity = identity;
        this.email = email;
        this.password = password;
        this.profileCharacter = profileCharacter;
        this.socialType = socialType;
    }


}
