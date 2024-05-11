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
        String minute = String.valueOf(time.getMinute());
        this.noon = time.getHour() <= 12 ? AM : PM;
        if (noon.equals(PM)) hour -= 12;
        this.hour = String.valueOf(hour).length() == 1 ? "0" + hour : String.valueOf(hour);
        this.minute = minute.length() == 1 ? "0" + minute : minute;
    }

    public LocalTime toLocalTime() {
        LocalTime localTime = LocalTime.of(Integer.parseInt(this.hour), Integer.parseInt(this.minute));
        if (noon.equals(PM)) {
            return localTime.plusHours(12);
        } else return localTime;
    }
}
