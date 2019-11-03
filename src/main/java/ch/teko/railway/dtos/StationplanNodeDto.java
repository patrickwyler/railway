package ch.teko.railway.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StationplanNodeDto {

	Long id;

	String label;

	String shape;

	int size;

	String color;
}
