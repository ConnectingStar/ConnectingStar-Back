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
     * 별자리 이미지(별자리 오픈되기 전의 캐릭터 이미지)
     */
    @Column(name = "constellation_image", nullable = false)
    private String image;
    /**
     * 캐릭터 이미지
     */
    @Column(name = "constellation_character_image", nullable = false)
    private String characterImage;
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
