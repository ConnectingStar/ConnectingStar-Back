package connectingstar.tars.user.response;

/**
 * 온보딩 진행 여부 반환
 *
 * @author 김성수
 */
public class UserOnboardCheckResponse {
    private Boolean onboard;

    public UserOnboardCheckResponse(Boolean onboard) {
        this.onboard = onboard;
    }
}
