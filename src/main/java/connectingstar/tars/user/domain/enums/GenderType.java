package connectingstar.tars.user.domain.enums;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public enum GenderType {
  M("MALE"),
  F("FEMALE"),
  N("NONE");

  private final String type;

  public String getType() {
    return type;
  }


}
