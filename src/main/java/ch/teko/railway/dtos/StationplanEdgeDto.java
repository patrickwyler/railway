package ch.teko.railway.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StationplanEdgeDto {

	Long from;

	Long to;

	int length;

}

