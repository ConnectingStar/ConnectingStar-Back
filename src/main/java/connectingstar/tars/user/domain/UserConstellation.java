package connectingstar.tars.user.domain;

import connectingstar.tars.constellation.domain.Constellation;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원이 보유한 별자리(캐릭터)
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
     * 회원 별자리 ID
     */
    @Id
    @Column(name = "user_constellation_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userConstellationId;

    /**
     * 회원이 사용한 별 개수
     */
    @Column(name = "star_count", nullable = false)
    private Integer starCount = 0;

    /**
     * 별자리 등록 여부
     * 보유 여부
     */
    @Column(name = "reg_yn", nullable = false)
    private Boolean regYn = Boolean.FALSE;

    /**
     * 회원
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 별자리
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "constellation_id")
    private Constellation constellation;

    public UserConstellation(
            Constellation constellation
    ) {
        setConstellation(constellation);
    }

    @Builder
    public UserConstellation(
            User user,
            Constellation constellation
    ) {
        setUser(user);
        setConstellation(constellation);
    }

    /**
     * 사용한 별 개수 수정
     */
    public void updateStarCount(Integer starCount) {
        setStarCount(starCount);

        if (starCount >= constellation.getStarCount()) {
            setRegYn(Boolean.TRUE);
        }
    }

    void setUser(User user) {
        this.user = user;
    }

    private void setConstellation(Constellation constellation) {
        this.constellation = constellation;
    }

    private void setStarCount(Integer starCount) {
        this.starCount = starCount;
    }

    private void setRegYn(Boolean regYn) {
        this.regYn = regYn;
    }
}
