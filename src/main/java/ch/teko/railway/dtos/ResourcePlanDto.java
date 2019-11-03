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
public class ResourcePlanDto {

    int amountOfTrains;

    int amountOfCarriages;

    HashMap<String, List<String>> trainsWithCarriages = new HashMap<>();

}
