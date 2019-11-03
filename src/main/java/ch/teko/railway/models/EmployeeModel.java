package ch.teko.railway.models;

import ch.teko.railway.enums.Function;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeModel {

    Long id;

    String firstname;

    String lastname;

    Function function;

    List<AbsenceModel> absences;
}
