package connectingstar.tars.user.domain;

import connectingstar.tars.user.domain.enums.AgeRangeType;
import connectingstar.tars.user.domain.enums.GenderType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "delete_account_reason", catalog = "tars")
public class DeleteAccountReason {

  @Id
  @Column(name = "delete_account_reason_id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer deleteAccountReasonId;

  /**
   * 탈퇴 이유
   */
  private String reason;

  /**
   * 성별 남(M),여(W),선택안함(N)
   */
  @Enumerated(value = EnumType.STRING)
  private GenderType genderType;

  /**
   * 나이 연령대
   */
  @Enumerated(value = EnumType.STRING)
  private AgeRangeType ageRange;

  /**
   * 가입 날짜
   */
  @Column(name = "created_dt", nullable = false)
  private String createdDt;

  /**
   * 탈퇴 날짜
   */
  @Column(name = "deleted_dt", nullable = false)
  private String deletedDt;

  @Builder
  public DeleteAccountReason(String reason, GenderType genderType, AgeRangeType ageRange,
      String createdDt,
      String deletedDt) {
    this.reason = reason;
    this.genderType = genderType;
    this.ageRange = ageRange;
    this.createdDt = createdDt;
    this.deletedDt = deletedDt;
  }
}
