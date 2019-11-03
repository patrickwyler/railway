package ch.teko.railway.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;


@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Line {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;

	@Column(length = 100)
	String name;

	@Column(length = 20)
	int lineNumber;

	@OneToMany(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, orphanRemoval = true)
	@JoinColumn(name = "line_id")
	@OrderColumn
	List<LinePart> lineParts;
}

