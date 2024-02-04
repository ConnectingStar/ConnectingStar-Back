package connectingstar.tars.user.domain;

import lombok.RequiredArgsConstructor;

import java.util.Locale;

@RequiredArgsConstructor
public enum SocialType {
    GOOGLE("GOOGLE"),
    KAKAO("KAKAO");

    private final String type;

    public String getType() {
        return type;
    }

    public static SocialType fromName(String type){
        return SocialType.valueOf(type.toUpperCase(Locale.ENGLISH));
    }
}
