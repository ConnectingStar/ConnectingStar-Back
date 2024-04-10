package connectingstar.tars.user.domain;

import connectingstar.tars.user.domain.enums.GenderType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "out", catalog = "tars")
public class UserOut {

  @Id
  @Column(name = "out_id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer outId;

  /**
   * 탈퇴 이유
   */
  private String reason;

  /**
   * 성별 남(M),여(W),선택안함(N)
   */
  @Enumerated(EnumType.STRING)
  private GenderType genderType;

  /**
   * 나이 연령대
   */
  private String ageRange;

  /**
   * 가입 날짜
   */
  @Column(name = "create_date", nullable = false)
  private LocalDateTime createDate;

  /**
   * 탈퇴 날짜
   */
  @Column(name = "delete_date", nullable = false)
  private LocalDateTime deleteDate;

  @Builder
  public UserOut(String reason, GenderType genderType, String ageRange, LocalDateTime createDate,
      LocalDateTime deleteDate) {
    this.reason = reason;
    this.genderType = genderType;
    this.ageRange = ageRange;
    this.createDate = createDate;
    this.deleteDate = deleteDate;
  }
}
