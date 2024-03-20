package connectingstar.tars.constellation.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 유저와 별자리 연관 엔티티
 *
 * @author 김규리
 */
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Access(AccessType.FIELD)
@Table(name = "constellation_having")
public class ConstellationHaving {

    @Id
    @Column(name = "constellation_having", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer constellationHavingId;

    @Column(name = "constellation_id", nullable = false)
    private Integer constellationId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;
}
