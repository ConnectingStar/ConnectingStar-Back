package connectingstar.tars.user.oauth;

import connectingstar.tars.user.domain.SocialType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {

    /* 각 소셜 타입의 AuthCode 요청 Url Redirect */
    @GetMapping("/{socialType}")
    public ResponseEntity<?> redirectAuthCodeRequestUrl(@PathVariable SocialType socialType) throws IOException {

        return ResponseEntity.ok(HttpStatus.OK);
    }
}
