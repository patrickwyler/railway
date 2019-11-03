package ch.teko.railway.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashMap;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WorkScheduleModel {

    int amountOfTrains;

    int amountOfEmployees;

    HashMap<String, List<EmployeeModel>> trainCrews = new HashMap<>();

}
