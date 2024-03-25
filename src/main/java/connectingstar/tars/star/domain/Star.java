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

    @OneToOne(mappedBy = "star")
    private User user;
}
