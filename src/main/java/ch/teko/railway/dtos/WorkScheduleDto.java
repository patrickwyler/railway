package ch.teko.railway.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashMap;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WorkScheduleDto {

    int amountOfTrains;

    int amountOfEmployees;

    HashMap<String, List<EmployeeDto>> trainCrews = new HashMap<>();

}
