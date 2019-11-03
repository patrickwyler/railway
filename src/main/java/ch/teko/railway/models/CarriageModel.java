package ch.teko.railway.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.Date;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarriageModel {

    @EqualsAndHashCode.Include
    Long id;

    String name;

    String typeDesignation;

    Date commissioningDate;

    Date lastAuditDate;

    Date nextAuditDate;

    int amountOfSeats;

    boolean available;
}
