package ch.teko.railway.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.LinkedHashMap;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TimeTableDto {

    LinkedHashMap<Integer, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, List<String>>>>> timeTable;

}
