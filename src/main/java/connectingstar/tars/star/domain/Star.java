package connectingstar.tars.star.domain;

import connectingstar.tars.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Star {

    @Id
    @Column(name = "star_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cnt", nullable = false)
    private Integer cnt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * 별 사용
     */
    public void updateStarCnt() {
        this.cnt -= 1;
    }
}
