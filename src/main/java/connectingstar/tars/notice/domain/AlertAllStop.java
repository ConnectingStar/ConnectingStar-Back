package connectingstar.tars.notice.domain;


import connectingstar.tars.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 약속 전체 일시 알람 엔티티
 * @author Gyuri Kim
 */
@Entity
@Getter
@NoArgsConstructor
public class AlertAllStop {

    @Id
    @Column(name = "alert_all_stop_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 알람 전체 일시종료 시작날짜
     */
    @Column(name = "start_date",nullable = false)
    private LocalDateTime startDate;

    /**
     * 알람 전체 일시종료 종료날짜
     */
    @Column(name = "end_date",nullable = false)
    private LocalDateTime endDate;
}
