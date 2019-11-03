package ch.teko.railway.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AbsenceModel {

    Long id;

    Date time;
}
