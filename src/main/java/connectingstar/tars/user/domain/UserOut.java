package connectingstar.tars.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private Gender gender;

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
    public UserOut(String reason, Gender gender, String ageRange, LocalDateTime createDate, LocalDateTime deleteDate) {
        this.reason = reason;
        this.gender = gender;
        this.ageRange = ageRange;
        this.createDate = createDate;
        this.deleteDate = deleteDate;
    }
}
