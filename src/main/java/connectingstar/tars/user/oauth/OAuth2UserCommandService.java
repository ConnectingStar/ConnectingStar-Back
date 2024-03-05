package connectingstar.tars.user.oauth;

import connectingstar.tars.user.domain.SocialType;
import connectingstar.tars.user.domain.User;
import connectingstar.tars.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2UserCommandService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    private static final String KAKAO = "kakao";

    /* 소셜 로그인 후 소셜로부터 받은 userRequest 데이터에 대한 후처리 되는 함수*/
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("CustomOAuth2UserService.loadUser() 실행 - OAuth2 로그인 요청 진입");

        /**
         * DefaultOAuth2UserService 객체를 생성하여, loadUser(userRequest)를 통해 DefaultOAuth2User 객체를 생성 후 반환
         * DefaultOAuth2UserService의 loadUser()는 소셜 로그인 API의 사용자 정보 제공 URI로 요청을 보내서
         * 사용자 정보를 얻은 후, 이를 통해 DefaultOAuth2User 객체를 생성 후 반환한다.
         * 결과적으로, OAuth2User = OAuth 서비스에서 가져온 유저 정보를 담고 있는 유저
         */

        //방법1
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        //방법2 - DefaultOAuth2UserService implement 하는 것과 어떤 차이가 있을까
        //OAuth2User oAuth2User = super.loadUser(userRequest);


        /** 서비스를 구분해주는 코드 (구글/카카오)
         * registrationId 추출 후 registrationId 으로 SocialType 저장
         * http://localhost:8080/oauth2/authorization/kakao에서 kakao가 registrationId
         */
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        SocialType socialType = getSocialType(registrationId);

        /**
         * OAuth2 로그인 진행시 키가 되는 필드값 프라이머리키와 같은 값 (네이버 카카오 지원 x)
         * userNameAttributeName은 이후에 nameAttributeKey로 설정된다.
         */
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName(); // OAuth2 로그인 시 키(PK)가 되는 값


        Map<String, Object> attributes = oAuth2User.getAttributes(); // 소셜 로그인에서 API가 제공하는 userInfo의 Json 값(유저 정보들)

        /**
         * userNameAttributeName은 이후에 nameAttributeKey로 설정된다.
         * socialType에 따라 유저 정보를 통해 OAuthAttributes 객체 생성
         */
        OAuthAttributes extractAttributes = OAuthAttributes.of(socialType, userNameAttributeName, attributes);

        User createdUser = getUser(extractAttributes, socialType); // getUser() 메소드로 User 객체 생성 후 반환


        /**
         * 로그인한 유저 리턴
         * DefaultOAuth2User 를 구현한 CustomOAuth2User 객체를 생성해서 반환
         */
        return new CustomOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(createdUser.getRole().getKey())),
                attributes,
                extractAttributes.getNameAttributeKey(),
                createdUser.getEmail(),
                createdUser.getRole()
        );
    }

    private SocialType getSocialType(String registrationId) {
        if(KAKAO.equals(registrationId)) {
            return SocialType.KAKAO;
        }
        return SocialType.GOOGLE;
    }

    /**
     * SocialType과 attributes에 들어있는 소셜 로그인의 식별값 id를 통해 회원을 찾아 반환하는 메소드
     * 만약 찾은 회원이 있다면 -> 그대로 반환
     * 만약 새로운 회원이라면 -> saveUser()를 호출하여 회원을 저장한다.
     */
    private User getUser(OAuthAttributes attributes, SocialType socialType) {
        User findUser = userRepository.findBySocialTypeAndSocialId(
                socialType, attributes.getOauth2UserInfo().getId())
                .orElse(null);

        if(findUser == null) {
            return saveUser(attributes, socialType);
        }
        return findUser;
    }

    /**
     * OAuthAttributes의 toEntity() 메소드를 통해 빌더로 User 객체 생성 후 반환
     * 생성된 User 객체를 DB에 저장 : socialType, socialId, email, role 값만 있는 상태
     */
    private User saveUser(OAuthAttributes attributes, SocialType socialType) {
        User createdUser = attributes.toEntity(socialType, attributes.getOauth2UserInfo());
        return userRepository.save(createdUser);
    }
}
