package connectingstar.tars.constellation.domain;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
  @OneToOne
  @JoinColumn(name = "constellation_id", nullable = false)
  private Constellation constellation;
}
