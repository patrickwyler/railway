package ch.teko.railway.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;


@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Trace extends LinePart {

	@Column(length = 100)
	String name;

	@Column(length = 20)
	int distanceInMinutes;

	@Column(length = 20)
	double distanceInKm;

}

