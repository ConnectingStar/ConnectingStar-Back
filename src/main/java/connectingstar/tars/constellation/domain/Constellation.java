package connectingstar.tars.constellation.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 별자리(캐릭터) 엔티티
 *
 * @author 송병선
 */
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Access(AccessType.FIELD)
@Table(name = "constellation")
public class Constellation {

    /**
     * 별자리(캐릭터) ID
     */
    @Id
    @Column(name = "constellation_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer constellationId;
    /**
     * 이름
     */
    @Column(name = "constellation_name", nullable = false)
    private String name;
    /**
     * 스토리
     */
    @Column(name = "constellation_story", nullable = false)
    private String story;
    /**
     * 정체성
     */
    @Column(name = "constellation_identity", nullable = false)
    private String identity;

    /**
     * 별자리 이미지, 흑백
     * 해금 완료 시 이벤트성
     */
    @Column(name = "constellation_image", nullable = false)
    private String image;

    /**
     * 캐릭터 이미지, 컬러
     * 해금 완료 시 보여지는 캐릭터 이미지, 별자리 카드 상세 조회 페이지
     */
    @Column(name = "constellation_character_image", nullable = false)
    private String characterImage;

    /**
     * 별자리 메인 이미지
     * 별자리 뼈대 이미지
     */
    @Column(name = "constellation_main_image", nullable = false)
    private String mainImage;

    /**
     * 별 개수
     */
    @Column(name = "constellation_star_count", nullable = false)
    private Integer starCount;

    ///////////////////////////////////////////////////////////
    // Relations
    ///////////////////////////////////////////////////////////
    /**
     * 별자리 타입 정보
     */
    @ManyToOne
    @JoinColumn(name = "constellation_type_id")
    private ConstellationType type;
    /**
     * 별자리 SVG 정보
     */
    @OneToOne(mappedBy = "constellation")
    private ConstellationSvg svg;
}
