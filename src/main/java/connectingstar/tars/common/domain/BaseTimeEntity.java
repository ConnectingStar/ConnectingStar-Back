package connectingstar.tars.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 공통 시간 엔티티
 * TODO: Auditable과 중복된 역할. 통일 필요.
 *
 * @author 송병선
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {

  /**
   * 등록시간
   */
  @CreatedDate
  @Column(name = "created_dt", updatable = false, nullable = false)
  protected LocalDateTime createdDt;
  /**
   * 수정시간
   */
  @CreatedDate
  @Column(name = "updated_dt", nullable = false)
  protected LocalDateTime updatedDt;
}
