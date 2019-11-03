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
public class LinePathModel {

    LineModel line;
    List<LinePartModel> paths = new ArrayList<>();

    public StationModel getStartStation() {
        return (StationModel) getPaths().get(0);
    }
}
