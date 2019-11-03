package ch.teko.railway.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Date;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(length = 100)
    String name;

    @Column(length = 100)
    String typeDesignation;

    @Temporal(TemporalType.DATE)
    Date commissioningDate;

    @Temporal(TemporalType.DATE)
    Date lastAuditDate;

    @Temporal(TemporalType.DATE)
    Date nextAuditDate;

    @Column(length = 10)
    int amountOfSeats;

    boolean available;
}
