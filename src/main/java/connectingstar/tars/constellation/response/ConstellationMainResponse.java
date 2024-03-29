package connectingstar.tars.constellation.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import connectingstar.tars.common.utils.FormatUtils;
import connectingstar.tars.constellation.domain.Constellation;
import connectingstar.tars.constellation.domain.ConstellationPath;
import connectingstar.tars.constellation.domain.ConstellationSvg;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import lombok.Getter;

/**
 * 별자리 메인 페이지 정보 반환
 *
 * @author 송병선
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConstellationMainResponse {

  /**
   * 보유 별 개수
   */
  private Integer starCount;
  /**
   * 캐릭터 이름
   */
  private String name;
  /**
   * svg 정보
   */
  private Svg svg;

  public ConstellationMainResponse(Integer starCount, Constellation constellation, Integer regStarCount) {
    this.starCount = starCount;
    this.name = constellation.getName();
    this.svg = new Svg(constellation.getSvg(), regStarCount);
  }

  @Getter
  @JsonInclude(JsonInclude.Include.NON_NULL)
  public class Svg {

    /**
     * fill
     */
    private String fill;
    /**
     * stroke
     */
    private String stroke;
    /**
     * strokeWidth
     */
    private BigDecimal strokeWitdh;
    /**
     * opacity
     */
    private BigDecimal opacity;
    /**
     * width
     */
    private BigDecimal width;
    /**
     * height
     */
    private BigDecimal height;
    /**
     * viewBox
     */
    private String viewBox;
    /**
     * pathList
     */
    private List<String> pathList;
    /**
     * circleList
     */
    private List<Circle> circleList;

    public Svg(ConstellationSvg svg, Integer regStarCount) {
      this.fill = svg.getFill();
      this.stroke = svg.getStroke();
      this.strokeWitdh = FormatUtils.format(svg.getStrokeWitdh());
      this.opacity = FormatUtils.format(svg.getOpacity());
      this.width = FormatUtils.format(svg.getWidth());
      this.height = FormatUtils.format(svg.getHeight());
      this.viewBox = svg.getViewBox();
      this.pathList = svg.getPathList().stream().map(ConstellationPath::getPath).toList();
      this.circleList = svg.getCircleList().stream()
          .map(circle -> new Circle(circle.getCx(), circle.getCy(), circle.getR()))
          .toList();
      for (int i = 0; i < regStarCount; i++) {
        circleList.get(i).setFilled(Boolean.TRUE);
      }
    }

    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public class Circle {

      /**
       * cx
       */
      private BigDecimal cx;
      /**
       * cy
       */
      private BigDecimal cy;
      /**
       * r
       */
      private BigDecimal r;
      /**
       * filled
       */
      private Boolean filled = Boolean.FALSE;

      public Circle(BigDecimal cx, BigDecimal cy, BigDecimal r) {
        this.cx = FormatUtils.format(cx);
        this.cy = FormatUtils.format(cy);
        this.r =  FormatUtils.format(r);
      }

      void setFilled(Boolean filled) {
        this.filled = filled;
      }
    }
  }
}
