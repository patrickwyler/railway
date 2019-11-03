package ch.teko.railway.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StationDto extends LinePartDto {

	Long id;

	String name;

	boolean rootStation;

	int stopTimeInMinutes;

	String errorMessage;
}
