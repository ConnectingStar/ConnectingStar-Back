package connectingstar.tars.device.domain;

import connectingstar.tars.common.audit.Auditable;
import connectingstar.tars.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

/**
 * 유저가 보유한 기기 (스마트폰)
 * 알림을 보낼 때 사용한다
 *
 * @author 이우진
 */
@Entity
@Table(indexes = @Index(name = "IX_Device_OwningUserId", columnList = "owning_user_id"))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Access(AccessType.FIELD)
public class Device extends Auditable {
    @Id
    @Column(name = "device_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 디바이스를 소유하고 있는 회원
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owning_user_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User owningUser;

    /**
     * FCM Registration Token.
     * Firebase로 알림을 보낼 때 사용하는 기기의 토큰.
     * 기기 하나 당 하나의 토큰이 발급된다
     */
    @Column(name = "fcm_registration_token")
    private String fcmRegistrationToken;

    /**
     * FCM Registration Token의 활성화 여부
     * 만료된 토큰이면 false
     */
    @Column(name = "is_fcm_token_active")
    @ColumnDefault("1")
    private Boolean isFcmTokenActive;

    @Builder(builderMethodName = "creationBuilder")
    public Device(User owningUser,
                  String fcmRegistrationToken,
                  Boolean isFcmTokenActive) {
        this.owningUser = owningUser;
        this.fcmRegistrationToken = fcmRegistrationToken;
        this.isFcmTokenActive = isFcmTokenActive;
    }
}
