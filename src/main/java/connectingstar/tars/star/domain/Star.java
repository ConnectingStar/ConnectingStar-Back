package connectingstar.tars.star.domain;

import connectingstar.tars.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Star {

    @Id
    @Column(name = "star_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    int cnt;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,CascadeType.MERGE} )
    @JoinColumn(name = "user_id",nullable = false)
    private User user;
}
