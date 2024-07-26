package connectingstar.tars.constellation.repository;

import connectingstar.tars.user.domain.UserConstellation;

import java.util.List;

/**
 * 사용자 별자리 QueryDSL Repository
 */
public interface UserConstellationRepositoryCustom {
    /**
     * 별자리 조회.
     * 조회는 fetch join을 사용.
     *
     * @param constellationTypeId (nullable) 별자리(캐릭터) 타입 ID
     * @param isRegistered        (nullable) 별자리 보유 여부
     * @param join                (nullable) 조인할 객체. "constellation" | "constellation_type"
     * @return 조회 결과
     */
    public List<UserConstellation> findMany(Integer constellationTypeId, Boolean isRegistered, List<String> join);
}
