package connectingstar.tars.star.controller;

import connectingstar.tars.star.command.StarCommandService;
import connectingstar.tars.user.domain.LoginUser;
import connectingstar.tars.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/star", produces = MediaType.APPLICATION_JSON_VALUE)
public class starController {
    private final StarCommandService starCommandService;
    @GetMapping(value = "/quit")
    public ResponseEntity<?> getStarCnt(@LoginUser User loginUser) {
        return ResponseEntity.ok(starCommandService.getStarCnt(loginUser));
    }

}
