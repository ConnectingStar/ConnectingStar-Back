package connectingstar.tars.constellation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 별자리(캐릭터) 관련 API
 *
 *  @author 송병선
 */
@RequiredArgsConstructor
@RequestMapping(value = "/constellation", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class ConstellationController {


}
