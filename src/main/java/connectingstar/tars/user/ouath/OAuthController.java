package connectingstar.tars.user.ouath;

import connectingstar.tars.user.domain.SocialType;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
