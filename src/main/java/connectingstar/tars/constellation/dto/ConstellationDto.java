package connectingstar.tars.constellation.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

/**
 * 별자리 단일 객체를 표현하는 DTO.
 * 연관 엔티티는 id만 포함.
 *
 * @author 이우진
 */
@Getter
@Setter
@JsonPropertyOrder({
        "constellationId",
        "name",
        "story",
        "identity",
        "image",
        "characterImage",
        "mainImage",
        "starCount",
        "constellationTypeId",
        "constellationType"
})
public class ConstellationDto {
    /**
     * 별자리(캐릭터) ID
     */
    private Integer constellationId;

    /**
     * 이름
     */
    private String name;

    /**
     * 스토리
     */
    private String story;

    /**
     * 정체성
     */
    private String identity;

    /**
     * 별자리 이미지, 흑백
     * 해금 완료 시 이벤트성
     */
    private String image;

    /**
     * 캐릭터 이미지, 컬러
     * 해금 완료 시 보여지는 캐릭터 이미지, 별자리 카드 상세 조회 페이지
     */
    private String characterImage;

    /**
     * 별자리 메인 이미지
     * 별자리 뼈대 이미지
     */
    private String mainImage;

    /**
     * 별 개수
     */
    private Integer starCount;

    /**
     * 별자리 타입 엔티티의 id
     */
    private Integer constellationTypeId;

    @Nullable
    private ConstellationTypeDto type;
}
