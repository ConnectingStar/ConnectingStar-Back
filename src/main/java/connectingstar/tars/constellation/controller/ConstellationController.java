package connectingstar.tars.constellation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 별자리(캐릭터) 관련 API.
 * REST API naming convention 준수하여 작성됨.
 *
 * @author 이우진
 */
@RequiredArgsConstructor
@RequestMapping(value = "/v2/constellations", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class ConstellationController {
}
