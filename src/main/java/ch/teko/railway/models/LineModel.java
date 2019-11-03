package ch.teko.railway.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LineModel {

    Long id;

    String name;

    int lineNumber;

    List<LinePartModel> lineParts = new ArrayList<>();

}
