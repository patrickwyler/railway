package ch.teko.railway.models;

import lombok.*;
import lombok.experimental.FieldDefaults;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TraceModel extends LinePartModel {

	Long id;

	String name;

	int distanceInMinutes;

}
