package connectingstar.tars.constellation.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 별자리(캐릭터) 타입 엔티티
 *
 * @author 송병선
 */
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Access(AccessType.FIELD)
@Table(name = "constellation_type")
public class ConstellationType {

    /**
     * 별자리(캐릭터) 타입 ID
     */
    @Id
    @Column(name = "constellation_type_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer constellationTypeId;
    /**
     * 이름
     */
    @Column(name = "constellation_type_name", nullable = false)
    private String name;
}
