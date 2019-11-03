package ch.teko.railway.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LineDto {

    Long id;

    String name;

    int lineNumber;

    List<LinePartDto> lineParts = new ArrayList<>();

    String errorMessage;
}
