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
public class AbsenceDto {

    Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date time;
}
