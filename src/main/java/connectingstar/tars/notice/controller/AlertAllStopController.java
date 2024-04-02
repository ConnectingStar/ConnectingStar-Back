package connectingstar.tars.notice.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/alert", produces = MediaType.APPLICATION_JSON_VALUE)
public class AlertAllStopController {

}
