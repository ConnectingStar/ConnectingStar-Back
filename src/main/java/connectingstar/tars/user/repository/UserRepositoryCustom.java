package connectingstar.tars.user.repository;

public interface UserRepositoryCustom {
    void addStar(Integer userId, Integer addedStarCount);

    /**
     * 유저가 해금 중인 별자리를 update합니다.
     */
    void updateUnlockingConstellation(Integer userId, Integer userConstellationId);
}
