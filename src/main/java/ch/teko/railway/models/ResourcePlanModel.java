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
public class ResourcePlanModel {

    int amountOfTrains;

    int amountOfCarriages;

    HashMap<String, List<String>> trainsWithCarriages = new HashMap<>();

}
