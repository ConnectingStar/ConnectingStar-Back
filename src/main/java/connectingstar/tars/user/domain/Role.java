package connectingstar.tars.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    GUEST("ROLE_GUEST"), //처음 가입한 사람
    USER("ROLE_USER"); //회원인 사람

    private final String key;

    public String getKey() {
        return key;
    }
}
