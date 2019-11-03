package ch.teko.railway.dtos;

import ch.teko.railway.enums.Function;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeDto {

    Long id;

    String firstname;

    String lastname;

    Function function;

    List<AbsenceDto> absences;
}
