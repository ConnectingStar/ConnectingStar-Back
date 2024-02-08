package connectingstar.tars.user.domain;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public enum Gender {
    M("MALE"),
    F("FEMALE"),
    N("NONE");

    private final String type;

    public String getType() {
        return type;
    }
}
