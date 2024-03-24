package connectingstar.tars.user.domain;

import connectingstar.tars.constellation.domain.Constellation;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사용자가 보유한 별자리(캐릭터)
 *
 * @author 송병선
 */
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Access(AccessType.FIELD)
@Table(name = "user_constellation", catalog = "tars")
@Inheritance(strategy = InheritanceType.JOINED)
public class UserConstellation {

  /**
   * 사용자 별자리 ID
   */
  @Id
  @Column(name = "user_constellation_id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userConstellationId;
  /**
   * 사용한 별 개수
   */
  @Column(name = "start_count", nullable = false)
  private Integer startCount;
  /**
   * 별자리 등록 여부
   */
  @Column(name = "reg_yn", nullable = false)
  private Boolean regYn = Boolean.FALSE;
  /**
   * 사용자
   */
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
  /**
   * 별자리
   */
  @ManyToOne
  @JoinColumn(name = "constellation_id")
  private Constellation constellation;

  public UserConstellation(Constellation constellation) {
    setConstellation(constellation);
  }

  /**
   * 사용한 별 개수 수정
   */
  public void modifyStarCount(Integer startCount) {
    setStartCount(startCount);

    if (startCount >= constellation.getStarCount()) {
      setRegYn(Boolean.TRUE);
    }
  }

  void setUser(User user) {
    this.user = user;
  }

  private void setConstellation(Constellation constellation) {
    this.constellation = constellation;
  }

  private void setStartCount(Integer startCount) {
    this.startCount = startCount;
  }

  private void setRegYn(Boolean regYn) {
    this.regYn = regYn;
  }
}
