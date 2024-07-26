package connectingstar.tars.constellation.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 별자리 SVG 정보 엔티티
 *
 * @author 송병선
 */
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Access(AccessType.FIELD)
@Table(name = "constellation_svg")
public class ConstellationSvg {

    /**
     * 별자리 svg circle 목록
     */
    @OneToMany(mappedBy = "svg", fetch = FetchType.LAZY)
    private final List<ConstellationCircle> circleList = new ArrayList<>();
    /**
     * 별자리 ID
     */
    @Id
    @Column(name = "constellation_id")
    private Integer constellationId;
    /**
     * fill
     */
    @Column(name = "fill", nullable = false)
    private String fill;
    /**
     * stroke
     */
    @Column(name = "stroke", nullable = false)
    private String stroke;
    /**
     * strokeWidth
     */
    @Column(name = "stroke_width", nullable = false)
    private BigDecimal strokeWidth;
    /**
     * opacity
     */
    @Column(name = "opacity", nullable = false)
    private BigDecimal opacity;
    /**
     * width
     */
    @Column(name = "width", nullable = false)
    private BigDecimal width;
    /**
     * height
     */
    @Column(name = "height", nullable = false)
    private BigDecimal height;
    /**
     * viewBox
     */
    @Column(name = "view_box", nullable = false)
    private String viewBox;

    ///////////////////////////////////////////////////////////
    // Relations
    ///////////////////////////////////////////////////////////
    /**
     * path
     */
    @Column(name = "path", nullable = false)
    private String path;
    /**
     * 별자리 정보
     */
    @MapsId
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "constellation_id", nullable = false)
    private Constellation constellation;
}
