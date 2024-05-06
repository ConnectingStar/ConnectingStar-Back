package connectingstar.tars.common.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;


@Getter
@NoArgsConstructor
public class TimeInfo {
    public static final String AM = "오전";
    public static final String PM = "오후";
    private String noon;
    private String hour;
    private String minute;

    public TimeInfo(LocalTime time) {
        Integer hour = time.getHour();
        this.noon = time.getHour() <= 12 ? AM : PM;
        this.hour = String.valueOf(hour).length() == 1 ? "0" + String.valueOf(hour).length() : String.valueOf(hour);
        this.minute = String.valueOf(time.getMinute());
    }
}
