package connectingstar.tars.constellation.domain;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 별자리 SVG Circle 엔티티
 *
 * @author 송병선
 */
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Access(AccessType.FIELD)
@Table(name = "constellation_circle")
public class ConstellationCircle {

    /**
     * 별자리 SVG Circle ID
     */
    @Id
    @Column(name = "constellation_circle_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer constellationCircleId;
    /**
     * cx
     */
    @Column(name = "cx", nullable = false)
    private BigDecimal cx;
    /**
     * cy
     */
    @Column(name = "cy", nullable = false)
    private BigDecimal cy;
    /**
     * r
     */
    @Column(name = "r", nullable = false)
    private BigDecimal r;

    ///////////////////////////////////////////////////////////
    // Relations
    ///////////////////////////////////////////////////////////
    /**
     * 별자리 SVG 정보
     */
    @ManyToOne
    @JoinColumn(name = "constellation_id")
    private ConstellationSvg svg;
}
