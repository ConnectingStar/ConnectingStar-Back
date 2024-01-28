package connectingstar.tars.constellation.controller;

import connectingstar.tars.constellation.command.ConstellationCommandService;
import connectingstar.tars.constellation.query.ConstellationQueryService;
import connectingstar.tars.constellation.query.ConstellationTypeQueryService;
import connectingstar.tars.constellation.request.ConstellationListRequest;
import connectingstar.tars.constellation.request.ConstellationOneRequest;
import connectingstar.tars.constellation.validation.ConstellationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    private final ConstellationCommandService constellationCommandService;
    private final ConstellationQueryService constellationQueryService;
    private final ConstellationTypeQueryService constellationTypeQueryService;

    @GetMapping(value = "/one")
    public ResponseEntity<?> getOne(ConstellationOneRequest param) {
        ConstellationValidator.validate(param);

        return ResponseEntity.ok(constellationQueryService.getOne(param));
    }

    @GetMapping(value = "/list")
    public ResponseEntity<?> getList(ConstellationListRequest param) {
        ConstellationValidator.validate(param);

        return ResponseEntity.ok(constellationQueryService.getList(param));
    }

    @GetMapping(value = "/type")
    public ResponseEntity<?> getTypeList() {
        return ResponseEntity.ok(constellationTypeQueryService.getList());
    }
}
