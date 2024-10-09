package connectingstar.tars.habit.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class HabitPostRequest {
    /**
     * 정체성
     */
    @Nonnull
    private String identity;

    /**
     * 실천 시간
     */
    @Nonnull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime runTime;

    /**
     * 장소
     */
    @Nonnull
    private String place;

    /**
     * 행동
     */
    @Nonnull
    private String action;

    /**
     * 얼마나
     */
    @Nonnull
    private Integer value;

    /**
     * 단위
     */
    @Nonnull
    private String unit;

    /**
     * 1차 알림 (값이 없을 시 자동으로 runTime 10분 전으로 설정)
     */
    @Nullable
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime firstAlert;

    /**
     * 2차 알림 (값이 없을 시 자동으로 runTime 30분 후로 설정)
     */
    @Nullable
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime secondAlert;

    /**
     * 온보딩 페이지에서 호출했으면 true.
     * 유저의 온보딩 정보를 업데이트한다.
     */
    @Nullable
    private Boolean isOnboarding;
}
