package connectingstar.tars.notice.controller;
import connectingstar.tars.notice.command.AlertStopCommandService;
import connectingstar.tars.notice.request.AlertStopRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/alert", produces = MediaType.APPLICATION_JSON_VALUE)
public class AlertStopController {

    private final AlertStopCommandService alertStopCommandService;
    @PostMapping
    public ResponseEntity<?> createAlertStop(@RequestBody AlertStopRequest alertStopRequest) {
        alertStopCommandService.saveAlertStop(alertStopRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
