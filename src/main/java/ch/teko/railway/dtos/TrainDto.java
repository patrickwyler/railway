package ch.teko.railway.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrainDto {

    Long id;

    String name;

    String typeDesignation;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date commissioningDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date lastAuditDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date nextAuditDate;

    int amountOfSeats;

    boolean available;
}
